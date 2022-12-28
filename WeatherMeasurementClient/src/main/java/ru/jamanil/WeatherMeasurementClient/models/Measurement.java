package ru.jamanil.WeatherMeasurementClient.models;

import lombok.Data;

/**
 * @author Victor Datsenko
 * 20.10.2022
 */
@Data
public class Measurement {
    private float value;
    private boolean raining;
    private Sensor sensor;
}
