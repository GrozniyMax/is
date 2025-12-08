package com.maxim.lab1.service.batch;

import com.maxim.lab1.db.BatchOperationDbService;
import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.model.BatchOperation;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.service.validation.BusinessValidationChain;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchUpdateService {

    private final FlatDbService flatDbService;

    private final BatchOperationDbService batchOperationDbService;


    @Transactional
    public BatchOperationResponse prepareAll(@Valid List<Flat> flats, String user) {
        boolean result = true;
        try {
            flats = flatDbService.prepareAll(flats);
        } catch (Exception e) {
            result = false;
        }

        var id = batchOperationDbService.prepareBatchOperation(new BatchOperation(null, user, ZonedDateTime.now(), result));
        return new BatchOperationResponse(id, flats);
    }

    @Transactional
    public void commitAll(BatchOperationResponse batchOperationResponse) {
        flatDbService.commitAll(batchOperationResponse.entities());
        batchOperationDbService.commitById(batchOperationResponse.batchId());
    }

    @Transactional
    public Long saveAll(@Valid List<Flat> flats, String user) {

        flats = flats
                .stream()
                .toList();

        boolean result = true;
        try {
            flatDbService.saveAll(flats);
        } catch (Exception e) {
            result = false;
        }

        var id = batchOperationDbService.save(new BatchOperation(null, user, ZonedDateTime.now(), result));
        return id;
    }

    public List<BatchOperation> getAllByUser(String user) {
        return batchOperationDbService.getAllByOwner(user);
    }
}
