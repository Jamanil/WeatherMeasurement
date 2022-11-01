package ru.jamanil.WeatherMeasurementServer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.WeatherMeasurementServer.model.Measurement;
import ru.jamanil.WeatherMeasurementServer.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public int findRainingDaysCount() {
        return measurementRepository.findAllByRaining(true).size();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        String sensorName = measurement.getSensor().getName();
        measurement.setSensor(sensorService.findByName(sensorName)
                .orElse(null));

        measurementRepository.save(measurement);
    }
}
