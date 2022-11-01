package ru.jamanil.WeatherMeasurementClient.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Victor Datsenko
 * 20.10.2022
 */
@Getter
@Setter
public class Measurement {
    private float value;
    private boolean raining;
    private Sensor sensor;
}
