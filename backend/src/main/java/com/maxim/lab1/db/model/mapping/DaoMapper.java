package com.maxim.lab1.db.model.mapping;

import com.maxim.lab1.db.model.HouseDao;
import com.maxim.lab1.db.model.mapping.GeometryMappingHelper;
import com.maxim.lab1.model.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DaoMapper {

    @Autowired
    protected GeometryMappingHelper geometryMappingHelper;

    @Mapping(target = "coordinates", expression = "java(geometryMappingHelper.toCoordinates(houseDao.getCoordinates()))")
    abstract House toHouse(HouseDao houseDao);

    @Mapping(target = "coordinates", expression = "java(geometryMappingHelper.toPolygon(houseDao.getCoordinates()))")
    abstract HouseDao toHouseDao(House house);
}
