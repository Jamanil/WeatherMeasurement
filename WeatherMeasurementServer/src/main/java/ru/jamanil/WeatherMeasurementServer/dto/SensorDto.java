package ru.jamanil.WeatherMeasurementServer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Getter
@Setter
public class SensorDto implements Serializable {
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
}
