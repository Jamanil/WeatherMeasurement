package ru.jamanil.WeatherMeasurementServer.utils;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
public class SensorRegistrationException extends RuntimeException {
    public SensorRegistrationException(String msg) {
        super(msg);
    }
}
