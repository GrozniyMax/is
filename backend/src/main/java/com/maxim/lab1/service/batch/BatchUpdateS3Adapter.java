package com.maxim.lab1.service.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.is.generated.dto.BatchOperationDto;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.lab1.controller.DtoMapper;
import com.maxim.lab1.db.BatchOperationDbService;
import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.service.validation.BusinessValidationChain;
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
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class BatchUpdateS3Adapter {

    private static final String BUCKET = "files";

    private final BatchOperationDbService batchOperationDbService;

    private final BusinessValidationChain businessValidationChain;

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
        var entities = validate(file);
        try {
            prepareData = retry(3, () -> prepareDb(entities, user));
            preparedFileName = retry(3, () -> prepareS3(file, prepareData.batchId()));
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

    private List<Flat> validate(MultipartFile file) {
        List<Flat> entities = null;
        try {
            entities = objectMapper.readValue(file.getBytes(), new TypeReference<List<FlatDto>>() {
                    })
                    .stream()
                    .map(dtoMapper::toFlat)
                    .peek(businessValidationChain::validate)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return entities;

    }


    private BatchOperationResponse prepareDb(List<Flat> entities, String user) {

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

    private <T> T retry(int attempts, Supplier<T> supplier) {
        RuntimeException exception = null;
        for (int i = 0; i < attempts; i++) {
            try {
                return supplier.get();
            } catch (RuntimeException e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
        return null;
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
