package com.maxim.lab1.model;

public class Flat {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float area; //Значение поля должно быть больше 0
    private Long price; //Значение поля должно быть больше 0
    private Boolean balcony; //Поле может быть null
    private float timeToMetroOnFoot; //Значение поля должно быть больше 0
    private int numberOfRooms; //Максимальное значение поля: 20, Значение поля должно быть больше 0
    private Integer floor; //Значение поля должно быть больше 0
    private boolean centralHeating;
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null
}

