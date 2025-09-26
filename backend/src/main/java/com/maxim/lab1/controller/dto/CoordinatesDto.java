package com.maxim.lab1.controller.dto;

import com.maxim.lab1.utils.ValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDto {

    @NotNull(message = ValidationMessages.NOT_NULL)
    private Float x; //Поле не может быть null

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Min(value = -166, message = ValidationMessages.MIN + "-166")
    private long y; //Значение поля должно быть больше -166
}
