package com.maxim.lab1.service.validation.impls;

import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.exception.BusinessException;
import com.maxim.lab1.model.exception.ErrorCodes;
import com.maxim.lab1.service.FlatRegistry;
import com.maxim.lab1.service.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SameHouseValidation implements Validator {

    FlatDbService flatDbService;

    @Override
    public void validate(Flat object) throws BusinessException {
        flatDbService.getFirstByHouseId(object.house().id())
                .ifPresent(fromDb -> compareAttributes(object, fromDb));
    }

    @Override
    public int priority() {
        return 1;
    }

    private void compareAttributes(Flat object, Flat fromDb) {
        if (compareWithEpsilon(object.timeToMetroOnFoot(), fromDb.timeToMetroOnFoot(), 0.0005F)) {
            throw createException();
        }
        if (object.centralHeating() != fromDb.centralHeating()) {
            throw createException();
        }
        if (object.transport() != fromDb.transport()) {
            throw createException();
        }
    }

    private boolean compareWithEpsilon(float first, float second, float epsilon) {
        return Math.abs(first - second) < epsilon;
    }

    private BusinessException createException() {
        return new BusinessException(ErrorCodes.CONSTRAINT_VIOLATION, "Такой дом уже существует, но аттрибуты дома у квартир расходятся");
    }
}
