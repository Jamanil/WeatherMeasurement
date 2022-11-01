package ru.jamanil.WeatherMeasurementServer.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.jamanil.WeatherMeasurementServer.dto.SensorDto;
import ru.jamanil.WeatherMeasurementServer.model.Sensor;
import ru.jamanil.WeatherMeasurementServer.services.SensorService;
import ru.jamanil.WeatherMeasurementServer.utils.ErrorResponse;
import ru.jamanil.WeatherMeasurementServer.utils.SensorRegistrationException;
import ru.jamanil.WeatherMeasurementServer.utils.SensorDtoUniqValidator;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;
    private final SensorDtoUniqValidator sensorDtoUniqValidator;
    private final ModelMapper modelMapper;

    @PostMapping("/registration")
    private ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDto sensorDto,
                                                      BindingResult bindingResult) {
        sensorDtoUniqValidator.validate(sensorDto, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(';');
            }
            throw new SensorRegistrationException(errorMsg.toString());
        } else {
            Sensor sensor = convertSensorDtoToSensor(sensorDto);
            sensorService.save(sensor);

            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    private Sensor convertSensorDtoToSensor(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorRegistrationException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
