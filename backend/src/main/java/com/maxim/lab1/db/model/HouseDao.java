package com.maxim.lab1.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "house")
public class HouseDao implements Comparable<HouseDao>{

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //Поле может быть null

    private long year; //Значение поля должно быть больше 0
    private int numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private long numberOfLifts; //Значение поля должно быть больше 0

    @Override
    public int compareTo(HouseDao o) {
        return Integer.compare(this.numberOfFlatsOnFloor, o.numberOfFlatsOnFloor);
    }
}
