package com.maxim.lab1.service.validation;

import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.exception.BusinessException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusinessValidationChain {

    List<Validator> validators;

    public BusinessValidationChain(List<Validator> rules) {
        this.validators = rules
                .stream()
                .sorted(Comparator.comparing(Validator::priority))
                .toList();
    }

    public void validate(Flat object) throws BusinessException {
        validators.forEach(validator -> validator.validate(object));
    }


}
