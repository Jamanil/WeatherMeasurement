package ru.jamanil.WeatherMeasurementServer.utils;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
public class MeasurementListEmptyException extends RuntimeException {
    public MeasurementListEmptyException(String msg) {
        super(msg);
    }
}
