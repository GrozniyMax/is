package com.maxim.lab1.service.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.is.generated.dto.BatchOperationDto;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.lab1.controller.DtoMapper;
import com.maxim.lab1.db.BatchOperationDbService;
import com.maxim.lab1.db.FlatDbService;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class BatchUpdateS3Adapter {

    private static final String BUCKET = "files";
    private static final long MULTIPART_SIZE = 1024;


    private final BatchOperationDbService batchOperationDbService;

    private final BatchUpdateService batchUpdateService;

    private final FlatDbService flatDbService;

    private final MinioClient minioClient;

    private final ObjectMapper objectMapper;

    private final DtoMapper dtoMapper;

    @Value("${minio.endpoint}")
    private String endpoint;


    @Transactional
    public void save(MultipartFile file, String user) throws IOException {
        BatchOperationResponse prepareData;
        String preparedFileName;
        try {
            prepareData = prepareDb(file, user);
            preparedFileName = prepareS3(file, prepareData.batchId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            retry(3, () -> commitDb(prepareData));
            retry(3, () ->commitS3(preparedFileName, prepareData.batchId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private BatchOperationResponse prepareDb(MultipartFile file, String user) throws IOException {
        var entities = objectMapper.readValue(file.getBytes(), new TypeReference<List<FlatDto>>() {
                })
                .stream()
                .map(dtoMapper::toFlat)
                .toList();

        return batchUpdateService.prepareAll(entities, user);
    }

    private String prepareS3(MultipartFile file, Long batchId) {
        var filename = "temp-" + batchId + ".json";

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("files")
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (ErrorResponseException | XmlParserException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                 ServerException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

    private void commitDb(BatchOperationResponse batchOperationResponse) {
        batchUpdateService.commitAll(batchOperationResponse);
    }

    private void commitS3(String tempFile, Long batchId){
        var name = batchId + ".json";

        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                            .bucket(BUCKET)
                            .source(CopySource.builder()
                                    .bucket(BUCKET)
                                    .object(tempFile)
                                    .build())
                            .object(name)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }

    }

    public List<BatchOperationDto> flatsUserGet(String user) {
        return batchUpdateService.getAllByUser(user)
                .stream()
                .map(operation -> dtoMapper.toBatchOperationDto(operation, createFileLink(operation.id())))
                .toList();
    }

    private String createFileLink(Long id) {
        return endpoint + "/" + BUCKET + "/" + id + ".json";
    }

    private void retry(int n, Runnable action) throws Exception {
        Exception failure = null;
        for (int i = 0; i < n; i++) {
            try {
                action.run();
                break;
            } catch (Exception e) {
                failure = e;
            }
        }

        if (failure != null) {
            throw failure;
        }
    }

}
