package com.maxim.lab1.service.validation.impls;

import com.maxim.lab1.db.HouseDbService;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.exception.BusinessException;
import com.maxim.lab1.model.exception.ErrorCodes;
import com.maxim.lab1.service.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HouseCoordinatesIntersectionValidator implements Validator {

    HouseDbService houseDbService;


    @Override
    public void validate(Flat object) throws BusinessException {
        if (houseDbService.houseWithIntersectionExists(object.house())) {
            throw new BusinessException(
                    ErrorCodes.CONSTRAINT_VIOLATION,
                    "Координаты дома пересекаются с координатами уже существующего в базе дома"
            );
        }
    }

    @Override
    public int priority() {
        return 3;
    }
}
