package com.maxim.lab1.db.model.mapping;

import com.maxim.lab1.model.Coordinates;
import com.maxim.lab1.model.Point;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.*;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeometryMappingHelper {

    GeometryFactory geometryFactory;

    @Named("toPolygon")
    public Polygon toPolygon(Coordinates coordinates) {
        if (coordinates == null) return null;

        double x1 = coordinates.first().x();
        double y1 = coordinates.first().y();
        double x2 = coordinates.second().x();
        double y2 = coordinates.second().y();

        Coordinate[] coords = new Coordinate[]{
                new Coordinate(x1, y1),
                new Coordinate(x1, y2),
                new Coordinate(x2, y2),
                new Coordinate(x2, y1),
                new Coordinate(x1, y1)
        };
        LinearRing shell = geometryFactory.createLinearRing(coords);
        return geometryFactory.createPolygon(shell, null);
    }

    @Named("toCoordinates")
    public Coordinates toCoordinates(Polygon polygon) {
        if (polygon == null) return null;

        Envelope env = polygon.getEnvelopeInternal();
        var first = new Point(env.getMinX(), env.getMinY());
        var second = new Point(env.getMaxX(), env.getMaxY());
        return new Coordinates(first, second);
    }
}
