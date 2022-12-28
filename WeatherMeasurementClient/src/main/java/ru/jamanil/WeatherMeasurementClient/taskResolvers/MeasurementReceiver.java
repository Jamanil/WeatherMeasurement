package ru.jamanil.WeatherMeasurementClient.taskResolvers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jamanil.WeatherMeasurementClient.models.Measurement;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Victor Datsenko
 * 20.10.2022
 * Класс, получающий замеры погоды из базы данных
 */
@Component
public class MeasurementReceiver {
    public static final Logger log = LoggerFactory.getLogger(MeasurementReceiver.class);
    private static final RestTemplate template = new RestTemplate();
    @Value("${weather_measurement.server_ip}")
    private String SERVER_IP;
    @Value("${weather_measurement.server_port}")
    private String SERVER_PORT;



    public List<Measurement> getMeasurements() {
        String url = String.format("http://%s:%s/measurements", SERVER_IP, SERVER_PORT);
        List<Measurement> measurements = List.of(Objects.requireNonNull(template.getForEntity(url, Measurement[].class).getBody()));

        log.info("Received {} measurements", measurements.size());

        return measurements;
    }
}
