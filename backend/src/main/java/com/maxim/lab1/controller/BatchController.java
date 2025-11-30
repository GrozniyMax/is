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
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BatchController implements BatchApi {

    BatchUpdateS3Adapter batchUpdateS3Adapter;

    @Override
    public ResponseEntity<Void> flatsUploadPost(String user, MultipartFile file) {
        try {
            batchUpdateS3Adapter.save(file, user);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<BatchOperationDto>> flatsUserGet(String user) {
        return ResponseEntity.ok(
                batchUpdateS3Adapter.flatsUserGet(user)
        );
    }
}
