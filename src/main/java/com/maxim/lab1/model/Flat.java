package com.maxim.lab1.model;

import lombok.With;

import java.time.ZonedDateTime;

/**
 * @param id                Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
 * @param name              Поле не может быть null, Строка не может быть пустой
 * @param coordinates       Поле не может быть null
 * @param creationDate      Поле не может быть null, Значение этого поля должно генерироваться автоматически
 * @param area              Значение поля должно быть больше 0
 * @param price             Значение поля должно быть больше 0
 * @param balcony           Поле может быть null
 * @param timeToMetroOnFoot Значение поля должно быть больше 0
 * @param numberOfRooms     Максимальное значение поля: 20, Значение поля должно быть больше 0
 * @param floor             Значение поля должно быть больше 0
 * @param transport         Поле не может быть null
 * @param house             Поле не может быть null
 */
@With
public record Flat(
        Long id,
        String name,
        Coordinates coordinates,
        ZonedDateTime creationDate,
        Float area,
        Long price,
        Boolean balcony,
        float timeToMetroOnFoot,
        int numberOfRooms,
        Integer floor,
        boolean centralHeating,
        Transport transport,
        House house) {
}

