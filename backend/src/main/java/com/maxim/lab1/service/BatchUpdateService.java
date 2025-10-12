package com.maxim.lab1.service;

import com.maxim.lab1.db.BatchOperationDbService;
import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.model.BatchOperation;
import com.maxim.lab1.model.Flat;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BatchUpdateService {

    FlatDbService flatDbService;

    BatchOperationDbService batchOperationDbService;

    @Transactional
    public void saveAll(@Valid List<Flat> flats, String user) {
        boolean result = true;
        try {
            flatDbService.saveAll(flats);
        } catch (Exception e) {
            result = false;
        }

        batchOperationDbService.save(new BatchOperation(user, LocalDateTime.now(), result));
    }

    public List<BatchOperation> getAllByUser(String user) {
        return batchOperationDbService.getAllByOwner(user);
    }
}
