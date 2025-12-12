package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.BatchOperationDao;
import com.maxim.lab1.db.model.TpcStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchOperationRepository extends JpaRepository<BatchOperationDao, Long> {

    List<BatchOperationDao> getAllByOwnerAndTpcStatus(String owner, TpcStatus tpcStatus);

    @Modifying
    @Query("UPDATE batch_operations b SET b.tpcStatus = :status WHERE b.id = :id")
    void setStatusById(Long id, TpcStatus status);

}
