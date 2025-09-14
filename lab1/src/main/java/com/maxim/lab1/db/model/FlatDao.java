package com.maxim.lab1.db.model;

import com.maxim.lab1.model.Transport;
import com.maxim.lab1.utils.ValidationMessages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Getter @Setter
@Entity(name = "flat")
public class FlatDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = ValidationMessages.NOT_NULL)
    @NotEmpty(message = ValidationMessages.STRING_NOT_EMPTY)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull(message = ValidationMessages.NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private CoordinatesDao coordinatesDao; //Поле не может быть null

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "creation_date", nullable = false, updatable = false)
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
    @Enumerated
    private Transport transport; //Поле не может быть null

    @NotNull(message = ValidationMessages.NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id", nullable = false)
    private HouseDao houseDao; //Поле не может быть null

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now();
    }
}

