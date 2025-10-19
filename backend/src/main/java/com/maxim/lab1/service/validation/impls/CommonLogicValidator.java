package com.maxim.lab1.service.validation.impls;

import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.exception.BusinessException;
import com.maxim.lab1.model.exception.ErrorCodes;
import com.maxim.lab1.service.validation.Validator;
import org.springframework.stereotype.Component;


@Component
public class CommonLogicValidator implements Validator {

    @Override
    public void validate(Flat object) throws BusinessException {
        if (object.floor() == 1) {
            if (object.balcony()) {
                throw new BusinessException(
                        ErrorCodes.CONSTRAINT_VIOLATION,
                        "У квартиры на первом этаже не должно быть балкона"
                );
            }
        }
    }

    @Override
    public int priority() {
        return 0;
    }
}
