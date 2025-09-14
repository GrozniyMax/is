package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.FlatDao;
import com.maxim.lab1.db.model.HouseDao;
import com.maxim.lab1.model.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface FlatRepository extends PagingAndSortingRepository<FlatDao, Long>, JpaRepository<FlatDao, Long> {

    Page<FlatDao> findAllByName(String name, Pageable pageable);

    @Query("SELECT COUNT(f) FROM flat f WHERE f.houseDao > :house")
    long findCountByHouseGreaterThan(HouseDao house);

    List<FlatDao> findAllByNameStartingWith(String name);

    @Query("SELECT DISTINCT f.transport FROM flat f")
    Set<Transport> distinctTransport();

    @Query("SELECT SUM(f.price) FROM flat f")
    long getTotalCost();
}
