package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.HouseDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<HouseDao, Long> {

    Optional<HouseDao> findByNameAndYearAndNumberOfFlatsOnFloorAndNumberOfLifts(String name, long year, int numberOfFlatsOnFloor, long numberOfLifts);

}
