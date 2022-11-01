package ru.jamanil.WeatherMeasurementServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jamanil.WeatherMeasurementServer.model.Sensor;

import java.util.Optional;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
