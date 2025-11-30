package com.maxim.lab1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.is.generated.dto.BatchOperationDto;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.lab1.config.MinioConfig;
import com.maxim.lab1.db.repository.BatchOperationRepository;
import com.maxim.lab1.service.BatchUpdateService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchUpdateS3Adapter {

    private static final String BUCKET = "files";

    private final BatchUpdateService batchUpdateService;

    private final MinioClient minioClient;

    private final ObjectMapper objectMapper;

    private final DtoMapper dtoMapper;

    @Value("${minio.endpoint}")
    private String endpoint;


    @Transactional
    public void save(MultipartFile file, String user) throws IOException {
        val entities = objectMapper.readValue(file.getBytes(), new TypeReference<List<FlatDto>>() {
                })
                .stream()
                .map(dtoMapper::toFlat)
                .toList();

        val batchUploadId = batchUpdateService.saveAll(entities, user);

        val filename = batchUploadId + ".json";

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("files")
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException |
                 InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
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

}
