package com.maxim.lab1.controller;

import com.maxim.is.generated.dto.*;
import com.maxim.lab1.model.*;
import org.mapstruct.Mapper;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    TransportDto toTransportDto(Transport transport);
    Transport toTransport(TransportDto transportDto);

    PointDto toPointDto(Point point);
    Point toPoint(PointDto pointDto);

    CoordinatesDto toCoordinatesDto(Coordinates coordinates);
    Coordinates toCoordinates(CoordinatesDto coordinatesDto);

    HouseDto toHouseDto(House house);
    House toHouse(HouseDto houseDto);

    Flat toFlat(FlatCreateDto flatCreateDto);
    FlatDto toFlatDto(Flat flat);
    Flat toFlat(FlatDto flatDto);

    BatchOperationDto toBatchOperationDto(BatchOperation batchOperation);

    default OffsetDateTime toOffsetDateTime(ZonedDateTime dateTime) {
        return dateTime.toOffsetDateTime();
    }

    default ZonedDateTime toZonedDateTime(OffsetDateTime dateTime) {
        return dateTime.toZonedDateTime();
    }

}
