package ru.jamanil.WeatherMeasurementClient.taskResolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jamanil.WeatherMeasurementClient.models.Measurement;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Victor Datsenko
 * 20.10.2022
 */
@Component
@RequiredArgsConstructor
public class MeasurementReceiver {
    private final Environment environment;

    private static final RestTemplate template = new RestTemplate();

    public List<Measurement> getMeasurements() {

        String url = String.format("http://%s:%s/measurements",
                environment.getProperty("weather_measurement.server_ip"),
                environment.getProperty("weather_measurement.server_port"));

        return Arrays.asList(Objects.requireNonNull(template.getForEntity(url, Measurement[].class).getBody()));
    }
}
