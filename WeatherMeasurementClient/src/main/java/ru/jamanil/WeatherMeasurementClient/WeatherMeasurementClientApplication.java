package ru.jamanil.WeatherMeasurementClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.jamanil.WeatherMeasurementClient.taskResolvers.MeasurementFiller;
import ru.jamanil.WeatherMeasurementClient.taskResolvers.MeasurementGraphDrawer;
import ru.jamanil.WeatherMeasurementClient.taskResolvers.MeasurementReceiver;

// Ну да, в этом классе код писать не нужно, но я только методы проверить
@SpringBootApplication
@RequiredArgsConstructor
public class WeatherMeasurementClientApplication {
    private static MeasurementFiller measurementFiller;
    private static MeasurementReceiver measurementReceiver;
    private static MeasurementGraphDrawer measurementGraphDrawer;


    @Autowired
    public WeatherMeasurementClientApplication(MeasurementFiller measurementFiller,
                                               MeasurementReceiver measurementReceiver,
                                               MeasurementGraphDrawer measurementGraphDrawer) {
        WeatherMeasurementClientApplication.measurementFiller = measurementFiller;
        WeatherMeasurementClientApplication.measurementReceiver = measurementReceiver;
        WeatherMeasurementClientApplication.measurementGraphDrawer = measurementGraphDrawer;
    }

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(WeatherMeasurementClientApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        try {
            measurementFiller.fillMeasurementList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        measurementGraphDrawer.drawChart(measurementReceiver.getMeasurements());
    }











}
