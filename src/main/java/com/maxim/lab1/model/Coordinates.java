package com.maxim.lab1.model;

import lombok.Value;
import lombok.With;

/**
 * @param x Поле не может быть null
 * @param y Значение поля должно быть больше -166
 */
@With
public record Coordinates(
        Long id,
        Float x,
        long y) {
}
