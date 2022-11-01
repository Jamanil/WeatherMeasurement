package ru.jamanil.WeatherMeasurementServer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Getter
@Setter
public class MeasurementDto {

    @JsonProperty(value = "sensor", required = true)
    private SensorDto sensorDto;

    @Min(-100)
    @Max(100)
    @JsonProperty(value = "value", required = true)
    private float value;

    @JsonProperty(value = "raining", required = true)
    private boolean raining;

    @JsonCreator
    public MeasurementDto(@JsonProperty(value = "sensor", required = true) SensorDto sensorDto,
                          @JsonProperty(value = "value", required = true) float value,
                          @JsonProperty(value = "raining", required = true) boolean raining) {
        this.sensorDto = sensorDto;
        this.value = value;
        this.raining = raining;
    }

    public MeasurementDto() {
    }
}
