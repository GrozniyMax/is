package com.maxim.lab1.db;

import com.maxim.lab1.db.model.mapping.DaoMapper;
import com.maxim.lab1.db.repository.HouseRepository;
import com.maxim.lab1.model.House;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HouseDbService {

    DaoMapper mapper;

    HouseRepository houseRepository;

    public boolean houseWithIntersectionExists(House house) {
        return houseRepository
                .existsByCoordinates(
                        mapper
                                .toHouseDao(house)
                                .getCoordinates()
                );
    }
}
