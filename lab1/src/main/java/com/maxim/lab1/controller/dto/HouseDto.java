package com.maxim.lab1.controller.dto;

import com.maxim.lab1.utils.ValidationMessages;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HouseDto {

    @NotNull(message = ValidationMessages.NOT_NULL)
    private String name; //Поле может быть null

    @Positive(message = ValidationMessages.POSITIVE)
    private long year; //Значение поля должно быть больше 0

    @Positive(message = ValidationMessages.POSITIVE)
    private int numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    @Positive(message = ValidationMessages.POSITIVE)
    private long numberOfLifts; //Значение поля должно быть больше 0
}
