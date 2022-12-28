package ru.jamanil.WeatherMeasurementServer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jamanil.WeatherMeasurementServer.dto.SensorDto;
import ru.jamanil.WeatherMeasurementServer.services.SensorService;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Component
public class SensorDtoUniqValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorDtoUniqValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target,
                         Errors errors) {
        SensorDto sensorDto = (SensorDto) target;

        if (sensorService.findByName(sensorDto.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    "",
                    "Sensor with this name already registered");
        }
    }
}
