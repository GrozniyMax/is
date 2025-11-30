package com.maxim.lab1.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.lab1.db.BatchOperationDbService;
import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.model.BatchOperation;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.service.validation.BusinessValidationChain;
import io.minio.MinioClient;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchUpdateService {

    private final FlatDbService flatDbService;

    private final BusinessValidationChain businessValidationChain;

    private final BatchOperationDbService batchOperationDbService;


    @Transactional
    public Long saveAll(@Valid List<Flat> flats, String user) {
        flats = flats
                .stream()
                .peek(businessValidationChain::validate)
                .toList();

        boolean result = true;
        try {
            flatDbService.saveAll(flats);
        } catch (Exception e) {
            result = false;
        }

        return batchOperationDbService.save(new BatchOperation(null, user, ZonedDateTime.now(), result));
    }

    public List<BatchOperation> getAllByUser(String user) {
        return batchOperationDbService.getAllByOwner(user);
    }
}
