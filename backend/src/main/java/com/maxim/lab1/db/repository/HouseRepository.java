package com.maxim.lab1.db.repository;

import com.maxim.lab1.db.model.HouseDao;
import com.maxim.lab1.db.model.TpcStatus;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<HouseDao, Long> {

    Optional<HouseDao> findByNameAndYearAndNumberOfFlatsOnFloorAndNumberOfLiftsAndTpcStatus(String name, long year, int numberOfFlatsOnFloor, long numberOfLifts, TpcStatus tpcStatus);

    @Query("""
            SELECT EXISTS(
                SELECT 1
                FROM house h
                WHERE h.tpcStatus = com.maxim.lab1.db.model.TpcStatus.COMMITED AND intersects(h.coordinates, :coordinates) = true
                )
    """)
    Boolean existsByCoordinates(Polygon coordinates);

}
