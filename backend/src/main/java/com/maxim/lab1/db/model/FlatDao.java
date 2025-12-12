package com.maxim.lab1.db.model;

import com.maxim.lab1.model.Transport;
import com.maxim.lab1.utils.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.ZonedDateTime;

@Getter
@Entity(name = "flat")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FlatDao {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Setter
    @NotNull(message = ValidationMessages.NOT_NULL)
    @NotEmpty(message = ValidationMessages.STRING_NOT_EMPTY)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Setter
    @Column(name = "creation_date", nullable = false, updatable = false)
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Setter
    @Positive(message = ValidationMessages.POSITIVE)
    private Float area; //Значение поля должно быть больше 0

    @Setter
    @Positive(message = ValidationMessages.POSITIVE)
    private Long price; //Значение поля должно быть больше 0

    @Setter
    @NotNull(message = ValidationMessages.NOT_NULL)
    private Boolean balcony; //Поле может быть null

    @Setter
    @Positive(message = ValidationMessages.POSITIVE)
    private float timeToMetroOnFoot; //Значение поля должно быть больше 0

    @Setter
    @Max(value = 20, message = ValidationMessages.MAX + "20")
    @Positive(message = ValidationMessages.POSITIVE)
    private int numberOfRooms; //Максимальное значение поля: 20, Значение поля должно быть больше 0

    @Setter
    @Positive(message = ValidationMessages.POSITIVE)
    private Integer floor; //Значение поля должно быть больше 0

    @Setter
    private boolean centralHeating;

    @Setter
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Enumerated
    private Transport transport; //Поле не может быть null

    @Setter
    @NotNull(message = ValidationMessages.NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id", nullable = false)
    private HouseDao house; //Поле не может быть null

    @Enumerated(EnumType.STRING)
    private TpcStatus tpcStatus = TpcStatus.COMMITED;

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now();
    }

    public void synchronizeStatus() {
        house.setTpcStatus(tpcStatus);
    }

    public void setStatus(TpcStatus tpcStatus) {
        this.tpcStatus = tpcStatus;
        synchronizeStatus();
    }
}

