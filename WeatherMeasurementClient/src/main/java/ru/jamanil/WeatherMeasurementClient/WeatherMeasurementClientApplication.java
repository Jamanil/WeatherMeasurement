package ru.jamanil.WeatherMeasurementClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.jamanil.WeatherMeasurementClient.models.Measurement;
import ru.jamanil.WeatherMeasurementClient.taskResolvers.MeasurementFiller;
import ru.jamanil.WeatherMeasurementClient.taskResolvers.MeasurementGraphDrawer;
import ru.jamanil.WeatherMeasurementClient.taskResolvers.MeasurementReceiver;

import java.util.List;

@SpringBootApplication
public class WeatherMeasurementClientApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(WeatherMeasurementClientApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
        MeasurementFiller filler = context.getBean(MeasurementFiller.class);
        MeasurementReceiver receiver = context.getBean(MeasurementReceiver.class);
        MeasurementGraphDrawer graphDrawer = context.getBean(MeasurementGraphDrawer.class);


        filler.fillMeasurementList(1000); //Генерируем 1000 случайных значения измерений, заполняем ими БД
        List<Measurement> measurements = receiver.getMeasurements(); // Получаем значения измерений из БД
        graphDrawer.drawChart(measurements); // Выводим значения измерений на графике
    }











}
