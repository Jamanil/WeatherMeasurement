package ru.jamanil.WeatherMeasurementServer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Entity
@Getter
@Setter
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
}
