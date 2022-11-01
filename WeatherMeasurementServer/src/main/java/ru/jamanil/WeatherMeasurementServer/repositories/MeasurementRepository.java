package ru.jamanil.WeatherMeasurementServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jamanil.WeatherMeasurementServer.model.Measurement;

import java.util.List;

/**
 * @author Victor Datsenko
 * 19.10.2022
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAllByRaining(boolean raining);
}
