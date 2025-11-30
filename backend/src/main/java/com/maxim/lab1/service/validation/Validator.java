package com.maxim.lab1.service.validation;

import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.exception.BusinessException;

public interface Validator {

    public void validate(Flat object) throws BusinessException;

    public int priority();
}
