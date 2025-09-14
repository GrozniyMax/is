package com.maxim.lab1.db.model;

import com.maxim.lab1.model.Coordinates;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DaoMapper {

    CoordinatesDao toCoordinatesDao(Coordinates coordinates);
    Coordinates toCoordinates(CoordinatesDao coordinatesDao);

    House toHouse(HouseDao houseDao);
    HouseDao toHouseDao(House house);

    @Mapping(target = "coordinates", source = "coordinatesDao")
    @Mapping(target = "house", source = "houseDao")
    Flat toFlat(FlatDao flatDao);

    @Mapping(target = "coordinatesDao", source = "coordinates")
    @Mapping(target = "houseDao", source = "house")
    FlatDao toFlatDao(Flat flat);

}
