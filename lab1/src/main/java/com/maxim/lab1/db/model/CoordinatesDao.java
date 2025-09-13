package com.maxim.lab1.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Constraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(name = "coordinates")
public class CoordinatesDao {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x", nullable = false)
    private Float x; //Поле не может быть null

    @Column(name = "y")
    private long y; //Значение поля должно быть больше -166
}
