package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.CoordinatesDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinatesRepository extends JpaRepository<CoordinatesDao, Long> {


    Optional<CoordinatesDao> findByXAndY(Float x, long y);

}
