package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.BatchOperationDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchOperationRepository extends JpaRepository<BatchOperationDao, Long> {

    List<BatchOperationDao> getAllByOwnerOrderByCreationData(String owner);

}
