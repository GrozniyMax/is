package com.maxim.lab1.db;

import com.maxim.lab1.db.model.BatchOperationDao;
import com.maxim.lab1.db.model.TpcStatus;
import com.maxim.lab1.db.model.mapping.DaoMapper;
import com.maxim.lab1.db.repository.BatchOperationRepository;
import com.maxim.lab1.model.BatchOperation;
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
        return batchOperationRepository.getAllByOwnerAndTpcStatus(owner, TpcStatus.COMMITED)
                .stream()
                .map(daoMapper::toBatchOperation)
                .toList();
    }

    public Long prepareBatchOperation(BatchOperation batchOperation) {
        return save(batchOperation, TpcStatus.PREPARED);
    }

    public void commitById(Long id) {
        batchOperationRepository.setStatusById(id, TpcStatus.COMMITED);
    }

    public Long commitBatchOperation(BatchOperation batchOperation) {
        return save(batchOperation, TpcStatus.COMMITED);
    }

    public Long save(BatchOperation batchOperation) {
        return commitBatchOperation(batchOperation);
    }

    private Long save(BatchOperation batchOperation, TpcStatus status) {
        var batchOperationDao = daoMapper.toBatchOperationDao(batchOperation);
        batchOperationDao.setTpcStatus(status);
        return batchOperationRepository.save(batchOperationDao).getId();
    }
}
