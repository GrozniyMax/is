package com.maxim.lab1.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.locationtech.jts.geom.Polygon;

@Getter @Setter
@Entity(name = "house")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HouseDao implements Comparable<HouseDao>{

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //Поле может быть null

    private long year; //Значение поля должно быть больше 0

    private int numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    private long numberOfLifts; //Значение поля должно быть больше 0

    private Polygon coordinates;


    @Override
    public int compareTo(HouseDao o) {
        return Integer.compare(this.numberOfFlatsOnFloor, o.numberOfFlatsOnFloor);
    }
}
