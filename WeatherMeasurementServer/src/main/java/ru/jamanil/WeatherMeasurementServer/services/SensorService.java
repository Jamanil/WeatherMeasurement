package ru.jamanil.WeatherMeasurementServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.WeatherMeasurementServer.model.Sensor;
import ru.jamanil.WeatherMeasurementServer.repositories.SensorRepository;

import java.util.Optional;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
