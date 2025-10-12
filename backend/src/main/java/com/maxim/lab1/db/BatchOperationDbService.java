package com.maxim.lab1.db;

import com.maxim.lab1.db.model.DaoMapper;
import com.maxim.lab1.db.repository.BatchOperationRepository;
import com.maxim.lab1.model.BatchOperation;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BatchOperationDbService {

    BatchOperationRepository batchOperationRepository;

    DaoMapper daoMapper;

    public List<BatchOperation> getAllByOwner(String owner) {
        return batchOperationRepository.getAllByOwnerOrderByCreationData(owner)
                .stream()
                .map(daoMapper::toBatchOperation)
                .toList();
    }

    public void save(BatchOperation batchOperation) {
        batchOperationRepository.save(daoMapper.toBatchOperationDao(batchOperation));
    }
}
