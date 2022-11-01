package ru.jamanil.WeatherMeasurementClient.taskResolvers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jamanil.WeatherMeasurementClient.models.Measurement;
import ru.jamanil.WeatherMeasurementClient.models.Sensor;

import java.util.Random;

/**
 * @author Victor Datsenko
 * 20.10.2022
 */
@Component
@RequiredArgsConstructor
public class MeasurementFiller {
    private final Environment environment;
    private static final Random random = new Random();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate template = new RestTemplate();

    public void fillMeasurementList() throws JsonProcessingException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        Sensor sensor = new Sensor();
        sensor.setName("Sensor name");

        Measurement measurement = new Measurement();
        measurement.setSensor(sensor);

        String url = String.format("http://%s:%s/sensors/registration",
                environment.getProperty("weather_measurement.server_ip"),
                environment.getProperty("weather_measurement.server_port"));

        String jsonString = objectMapper.writeValueAsString(sensor);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        template.postForLocation(url, request);

        System.out.println("sensor registered");

        url = String.format("http://%s:%s/measurements/add",
                environment.getProperty("weather_measurement.server_ip"),
                environment.getProperty("weather_measurement.server_port"));

        for (int i = 0; i < 1000; i++) {
            measurement.setValue(100 - random.nextFloat() * 200);
            measurement.setRaining(random.nextBoolean());
            jsonString = objectMapper.writeValueAsString(measurement);
            request = new HttpEntity<>(jsonString, headers);
            template.postForLocation(url, request);
        }

        System.out.println("Filling complete");
    }
}
