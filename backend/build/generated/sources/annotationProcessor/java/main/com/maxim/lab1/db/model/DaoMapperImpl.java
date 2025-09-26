package com.maxim.lab1.db.model;

import com.maxim.lab1.model.Coordinates;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import com.maxim.lab1.model.Transport;
import java.time.ZonedDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T19:56:42+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class DaoMapperImpl implements DaoMapper {

    @Override
    public CoordinatesDao toCoordinatesDao(Coordinates coordinates) {
        if ( coordinates == null ) {
            return null;
        }

        CoordinatesDao coordinatesDao = new CoordinatesDao();

        coordinatesDao.setId( coordinates.id() );
        coordinatesDao.setX( coordinates.x() );
        coordinatesDao.setY( coordinates.y() );

        return coordinatesDao;
    }

    @Override
    public Coordinates toCoordinates(CoordinatesDao coordinatesDao) {
        if ( coordinatesDao == null ) {
            return null;
        }

        Long id = null;
        Float x = null;
        long y = 0L;

        id = coordinatesDao.getId();
        x = coordinatesDao.getX();
        y = coordinatesDao.getY();

        Coordinates coordinates = new Coordinates( id, x, y );

        return coordinates;
    }

    @Override
    public House toHouse(HouseDao houseDao) {
        if ( houseDao == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        long year = 0L;
        int numberOfFlatsOnFloor = 0;
        long numberOfLifts = 0L;

        id = houseDao.getId();
        name = houseDao.getName();
        year = houseDao.getYear();
        numberOfFlatsOnFloor = houseDao.getNumberOfFlatsOnFloor();
        numberOfLifts = houseDao.getNumberOfLifts();

        House house = new House( id, name, year, numberOfFlatsOnFloor, numberOfLifts );

        return house;
    }

    @Override
    public HouseDao toHouseDao(House house) {
        if ( house == null ) {
            return null;
        }

        HouseDao houseDao = new HouseDao();

        houseDao.setName( house.name() );
        houseDao.setYear( house.year() );
        houseDao.setNumberOfFlatsOnFloor( house.numberOfFlatsOnFloor() );
        houseDao.setNumberOfLifts( house.numberOfLifts() );
        houseDao.setId( house.id() );

        return houseDao;
    }

    @Override
    public Flat toFlat(FlatDao flatDao) {
        if ( flatDao == null ) {
            return null;
        }

        Coordinates coordinates = null;
        House house = null;
        Long id = null;
        String name = null;
        ZonedDateTime creationDate = null;
        Float area = null;
        Long price = null;
        Boolean balcony = null;
        float timeToMetroOnFoot = 0.0f;
        int numberOfRooms = 0;
        Integer floor = null;
        boolean centralHeating = false;
        Transport transport = null;

        coordinates = toCoordinates( flatDao.getCoordinates() );
        house = toHouse( flatDao.getHouse() );
        id = flatDao.getId();
        name = flatDao.getName();
        creationDate = flatDao.getCreationDate();
        area = flatDao.getArea();
        price = flatDao.getPrice();
        balcony = flatDao.getBalcony();
        timeToMetroOnFoot = flatDao.getTimeToMetroOnFoot();
        numberOfRooms = flatDao.getNumberOfRooms();
        floor = flatDao.getFloor();
        centralHeating = flatDao.isCentralHeating();
        transport = flatDao.getTransport();

        Flat flat = new Flat( id, name, coordinates, creationDate, area, price, balcony, timeToMetroOnFoot, numberOfRooms, floor, centralHeating, transport, house );

        return flat;
    }

    @Override
    public FlatDao toFlatDao(Flat flat) {
        if ( flat == null ) {
            return null;
        }

        FlatDao flatDao = new FlatDao();

        flatDao.setCoordinates( toCoordinatesDao( flat.coordinates() ) );
        flatDao.setHouse( toHouseDao( flat.house() ) );
        flatDao.setId( flat.id() );
        flatDao.setName( flat.name() );
        flatDao.setCreationDate( flat.creationDate() );
        flatDao.setArea( flat.area() );
        flatDao.setPrice( flat.price() );
        flatDao.setBalcony( flat.balcony() );
        flatDao.setTimeToMetroOnFoot( flat.timeToMetroOnFoot() );
        flatDao.setNumberOfRooms( flat.numberOfRooms() );
        flatDao.setFloor( flat.floor() );
        flatDao.setCentralHeating( flat.centralHeating() );
        flatDao.setTransport( flat.transport() );

        return flatDao;
    }
}
