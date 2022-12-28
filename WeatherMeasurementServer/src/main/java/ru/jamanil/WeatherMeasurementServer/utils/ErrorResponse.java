package ru.jamanil.WeatherMeasurementServer.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Data
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String errorMsg) {
        this.message = errorMsg;
        this.timestamp = LocalDateTime.now();
    }
}
