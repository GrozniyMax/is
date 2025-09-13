package com.maxim.lab1.controller.dto;

import com.maxim.lab1.utils.ValidationMessages;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;


@AllArgsConstructor
public class FlatDto {

    @Nullable
    private Long id;//Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = ValidationMessages.NOT_NULL)
    @NotEmpty(message = ValidationMessages.STRING_NOT_EMPTY)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull(message = ValidationMessages.NOT_NULL)
    private CoordinatesDto coordinates; //Поле не может быть null

    @Nullable
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Positive(message = ValidationMessages.POSITIVE)
    private Float area; //Значение поля должно быть больше 0

    @Positive(message = ValidationMessages.POSITIVE)
    private Long price; //Значение поля должно быть больше 0

    @NotNull(message = ValidationMessages.NOT_NULL)
    private Boolean balcony; //Поле может быть null

    @Positive(message = ValidationMessages.POSITIVE)
    private float timeToMetroOnFoot; //Значение поля должно быть больше 0

    @Max(value = 20, message = ValidationMessages.MAX + "20")
    @Positive(message = ValidationMessages.POSITIVE)
    private int numberOfRooms; //Максимальное значение поля: 20, Значение поля должно быть больше 0

    @Positive(message = ValidationMessages.POSITIVE)
    private Integer floor; //Значение поля должно быть больше 0

    private boolean centralHeating;

    @NotNull(message = ValidationMessages.NOT_NULL)
    private Transport transport; //Поле не может быть null

    @NotNull(message = ValidationMessages.NOT_NULL)
    private HouseDto house; //Поле не может быть null
}

