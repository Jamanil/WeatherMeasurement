package ru.jamanil.WeatherMeasurementServer.controllers;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.jamanil.WeatherMeasurementServer.dto.MeasurementDto;
import ru.jamanil.WeatherMeasurementServer.dto.SensorDto;
import ru.jamanil.WeatherMeasurementServer.model.Measurement;
import ru.jamanil.WeatherMeasurementServer.model.Sensor;
import ru.jamanil.WeatherMeasurementServer.services.MeasurementService;
import ru.jamanil.WeatherMeasurementServer.utils.ErrorResponse;
import ru.jamanil.WeatherMeasurementServer.utils.MeasurementAdditionException;
import ru.jamanil.WeatherMeasurementServer.utils.MeasurementDtoSensorExistenceValidator;
import ru.jamanil.WeatherMeasurementServer.utils.MeasurementListEmptyException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementDtoSensorExistenceValidator sensorExistenceValidator;
    private static boolean measurementsEmpty;


    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 MeasurementDtoSensorExistenceValidator sensorExistenceValidator) {
        this.measurementService = measurementService;
        this.sensorExistenceValidator = sensorExistenceValidator;
        measurementsEmpty = measurementService.findAll().isEmpty();
    }

    @GetMapping
    private ResponseEntity<List<MeasurementDto>> showAllMeasurements() {
        if (measurementsEmpty) {
            throw new MeasurementListEmptyException("Measurements list is empty");
        }
        List<MeasurementDto> measurementDtoList = measurementService.findAll()
                .stream().map(this::convertMeasurementToMeasurementDto).collect(Collectors.toList());
        return new ResponseEntity<>(measurementDtoList, HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    private ResponseEntity<Integer> showRainingDaysCount() {
        if (measurementsEmpty) {
            throw new MeasurementListEmptyException("Measurements list is empty");
        }
        return new ResponseEntity<>(measurementService.findRainingDaysCount(), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDto measurementDto,
                                                      BindingResult bindingResult) {
        sensorExistenceValidator.validate(measurementDto, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(';');
            }
            throw new MeasurementAdditionException(errorMessage.toString());
        }

        Measurement measurement = convertMeasurementDtoToMeasurement(measurementDto);
        measurementService.save(measurement);
        measurementsEmpty = false;

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementAdditionException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementListEmptyException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MismatchedInputException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getOriginalMessage()), HttpStatus.BAD_REQUEST);
    }

    private Measurement convertMeasurementDtoToMeasurement(MeasurementDto measurementDto) {
        Measurement measurement = new Measurement();

        Sensor sensor = new Sensor();
        sensor.setName(measurementDto.getSensorDto().getName());

        measurement.setSensor(sensor);
        measurement.setValue(measurementDto.getValue());
        measurement.setRaining(measurementDto.isRaining());

        return measurement;
    }

    private MeasurementDto convertMeasurementToMeasurementDto(Measurement measurement) {
        MeasurementDto measurementDto = new MeasurementDto();

        SensorDto sensorDto = new SensorDto();
        sensorDto.setName(measurement.getSensor().getName());

        measurementDto.setSensorDto(sensorDto);
        measurementDto.setValue(measurement.getValue());
        measurementDto.setRaining(measurement.isRaining());

        return measurementDto;
    }
}
