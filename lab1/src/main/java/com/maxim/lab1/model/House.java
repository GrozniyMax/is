package com.maxim.lab1.model;

import lombok.With;

/**
 * @param name                 Поле может быть null
 * @param year                 Значение поля должно быть больше 0
 * @param numberOfFlatsOnFloor Значение поля должно быть больше 0
 * @param numberOfLifts        Значение поля должно быть больше 0
 */
@With
public record House(
        Long id,
        String name,
        long year,
        int numberOfFlatsOnFloor,
        long numberOfLifts){

}
