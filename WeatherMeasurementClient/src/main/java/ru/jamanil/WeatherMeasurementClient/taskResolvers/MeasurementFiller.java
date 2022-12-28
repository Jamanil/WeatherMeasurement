package ru.jamanil.WeatherMeasurementClient.taskResolvers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jamanil.WeatherMeasurementClient.models.Measurement;
import ru.jamanil.WeatherMeasurementClient.models.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Victor Datsenko
 * 20.10.2022
 * Класс, заполняющий базу данных случайно сгенерированными измерениями погоды
 */
@Component
public class MeasurementFiller {
    private static final Logger log = LoggerFactory.getLogger(MeasurementGraphDrawer.class);

    private static final Random random = new Random();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate template = new RestTemplate();
    @Value("${weather_measurement.server_ip}")
    private String SERVER_IP;
    @Value("${weather_measurement.server_port}")
    private String SERVER_PORT;

    public void fillMeasurementList(int count) throws JsonProcessingException {

        Sensor sensor = new Sensor();
        sensor.setName("New sensor name");
        registerSensor(sensor);

        List<Measurement> measurements = generateRandomMeasurements(count, sensor);

        sendMeasurementsToServer(measurements);
    }

    private List<Measurement> generateRandomMeasurements(int count, Sensor sensor) throws JsonProcessingException {
        List<Measurement> measurements = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Measurement measurement = new Measurement();
            measurement.setSensor(sensor);
            measurement.setValue(100 - random.nextFloat() * 200);
            measurement.setRaining(random.nextBoolean());
            measurements.add(measurement);
        }
        log.info("Generated {} measurements", count);
        return measurements;
    }

    private void sendMeasurementsToServer(List<Measurement> measurements) throws JsonProcessingException {
        String url = String.format("http://%s:%s/measurements/add", SERVER_IP, SERVER_PORT);
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Measurement measurement : measurements) {
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(measurement), headers);
            template.postForLocation(url, request);
        }

        log.info("{} measurements sent to server", measurements.size());
    }

    private void registerSensor(Sensor sensor) throws JsonProcessingException {
        String url = String.format("http://%s:%s/sensors/registration", SERVER_IP, SERVER_PORT);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(sensor), headers);
        template.postForLocation(url, request);

        log.info("Sensor {} registered", sensor.getName());
    }
}
