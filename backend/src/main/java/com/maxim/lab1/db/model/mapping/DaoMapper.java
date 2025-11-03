package com.maxim.lab1.db.model.mapping;

import com.maxim.is.generated.dto.BatchOperationDto;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.lab1.db.model.BatchOperationDao;
import com.maxim.lab1.db.model.FlatDao;
import com.maxim.lab1.db.model.HouseDao;
import com.maxim.lab1.db.model.mapping.GeometryMappingHelper;
import com.maxim.lab1.model.BatchOperation;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DaoMapper {

    @Autowired
    protected GeometryMappingHelper geometryMappingHelper;

    @Mapping(target = "coordinates", expression = "java(geometryMappingHelper.toCoordinates(houseDao.getCoordinates()))")
    abstract public House toHouse(HouseDao houseDao);

    @Mapping(target = "coordinates", expression = "java(geometryMappingHelper.toPolygon(house.coordinates()))")
    abstract public HouseDao toHouseDao(House house);

    abstract public Flat toFlat(FlatDao flatDao);
    abstract public FlatDao toFlatDao(Flat flat);

    abstract public BatchOperation toBatchOperation(BatchOperationDao batchOperationDao);
    abstract public BatchOperationDao toBatchOperationDao(BatchOperation batchOperation);
}
