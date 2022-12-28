package ru.jamanil.WeatherMeasurementServer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jamanil.WeatherMeasurementServer.dto.MeasurementDto;
import ru.jamanil.WeatherMeasurementServer.dto.SensorDto;
import ru.jamanil.WeatherMeasurementServer.services.SensorService;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Component
public class MeasurementDtoSensorExistenceValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementDtoSensorExistenceValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target,
                               Errors errors) {
        MeasurementDto measurementDto = (MeasurementDto) target;
        SensorDto sensorDto = measurementDto.getSensorDto();

        if (sensorService.findByName(sensorDto.getName()).isEmpty()) {
            errors.rejectValue(
                    "sensorDto",
                    "",
                    "this sensor is not registered in the database");
        }
    }
}
