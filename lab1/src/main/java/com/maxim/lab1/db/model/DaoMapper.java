package com.maxim.lab1.db.model;

import com.maxim.lab1.model.Coordinates;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DaoMapper {

    CoordinatesDao toCoordinatesDao(Coordinates coordinates);
    Coordinates toCoordinates(CoordinatesDao coordinatesDao);

    House toHouse(HouseDao houseDao);
    HouseDao toHouseDao(House house);

    Flat toFlat(FlatDao flatDao);
    FlatDao toFlatDao(Flat flat);

}
