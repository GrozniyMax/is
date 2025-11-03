package com.maxim.lab1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.is.generated.dto.BatchOperationDto;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.is.generated.openapi.api.BatchApi;
import com.maxim.is.generated.openapi.api.SpecialOperationsApi;
import com.maxim.lab1.service.BatchUpdateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BatchController implements BatchApi {

    DtoMapper dtoMapper;

    BatchUpdateService batchUpdateService;

    ObjectMapper objectMapper;

    private List<FlatDto> read(MultipartFile file) throws IOException {
        return objectMapper.readValue(file.getBytes(), new TypeReference<List<FlatDto>>() {});
    }

    @Override
    public ResponseEntity<Void> flatsUploadPost(String user, MultipartFile file) {
        try {
            batchUpdateService.saveAll(
                    read(file).stream().map(dtoMapper::toFlat).toList(),
                    user
            );
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<BatchOperationDto>> flatsUserGet(String user) {
        return ResponseEntity.ok(
                batchUpdateService.getAllByUser(user)
                        .stream()
                        .map(dtoMapper::toBatchOperationDto)
                        .toList()
        );
    }
}
