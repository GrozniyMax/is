package com.maxim.lab1.controller.dto;

import com.maxim.lab1.model.Coordinates;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    CoordinatesDto toCoordinatesDto(Coordinates coordinates);
    Coordinates toCoordinates(CoordinatesDto coordinatesDto);

    HouseDto toHouseDto(House house);
    House toHouse(HouseDto houseDto);

    FlatDto toFlatDto(Flat flat);
    Flat toFlat(FlatDto flatDto);

}
