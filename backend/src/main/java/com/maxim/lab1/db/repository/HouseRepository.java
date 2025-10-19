package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.HouseDao;
import org.springframework.data.geo.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<HouseDao, Long> {

    Optional<HouseDao> findByNameAndYearAndNumberOfFlatsOnFloorAndNumberOfLifts(String name, long year, int numberOfFlatsOnFloor, long numberOfLifts);


    @Query("""
            SELECT EXISTS(
                SELECT 1
                FROM house h
                WHERE intersects(h.coordinates, :searchPolygon) = true
                )
    """)
    Boolean existsByCoordinates(Polygon coordinates);

}
