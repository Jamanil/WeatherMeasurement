package ru.jamanil.WeatherMeasurementServer.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String errorMsg) {
        this.message = errorMsg;
        this.timestamp = LocalDateTime.now();
    }
}
