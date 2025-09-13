package com.maxim.lab1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
public class CoordinatesDto {

    @NotNull(message = ValidationMessages.NOT_NULL)
    private Float x; //Поле не может быть null

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Min(value = -166, message = ValidationMessages.MIN + "-166")
    private long y; //Значение поля должно быть больше -166
}
