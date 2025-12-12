package com.maxim.lab1.db.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity(name = "batch_operations")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BatchOperationDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String owner;

    private ZonedDateTime creationData;

    private Boolean success;

    @Enumerated(EnumType.STRING)
    private TpcStatus tpcStatus = TpcStatus.PREPARED;
}
