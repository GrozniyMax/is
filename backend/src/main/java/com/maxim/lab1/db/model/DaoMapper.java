package com.maxim.lab1.db.model;

import com.maxim.lab1.model.BatchOperation;
import com.maxim.lab1.model.Coordinates;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

@Mapper(componentModel = "spring")
public interface DaoMapper {

    @Mapping(target = "coordinates", source = "coordinates", qualifiedByName = "toCoordinates")
    House toHouse(HouseDao houseDao);

    @Mapping(target = "coordinates", source = "coordinates", qualifiedByName = "toPolygon")
    HouseDao toHouseDao(House house);

    @Mapping(target = "coordinates", source = "coordinates")
    @Mapping(target = "house", source = "house")
    Flat toFlat(FlatDao flatDao);

    @Mapping(target = "coordinates", source = "coordinates")
    @Mapping(target = "house", source = "house")
    FlatDao toFlatDao(Flat flat);

    BatchOperation toBatchOperation(BatchOperationDao batchOperationDao);

    BatchOperationDao toBatchOperationDao(BatchOperation batchOperation);

    @Named("toPolygon")
    default Polygon toPolygon(Coordinates coordinates) {
        double x1 = coordinates.first().x();
        double y1 = coordinates.first().y();

        double x2 = coordinates.second().x();
        double y2 = coordinates.second().y();

        return new Polygon(
                new Point(x1, y1),
                new Point(x2, y1),
                new Point(x2, y2),
                new Point(x1, y2)
        );
    }

    @Named("tpCoordinates")
    default Coordinates toCoordinates(Polygon polygon) {
        var first = polygon.getPoints().get(0);
        var third = polygon.getPoints().get(2);

        return new Coordinates(
                new com.maxim.lab1.model.Point(first.getX(), first.getY()),
                new com.maxim.lab1.model.Point(third.getX(), third.getY())
        );
    }

}
