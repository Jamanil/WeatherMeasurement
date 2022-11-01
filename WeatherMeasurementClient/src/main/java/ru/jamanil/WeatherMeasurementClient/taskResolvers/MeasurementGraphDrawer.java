package ru.jamanil.WeatherMeasurementClient.taskResolvers;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.stereotype.Component;
import ru.jamanil.WeatherMeasurementClient.models.Measurement;

import java.util.List;

/**
 * @author Victor Datsenko
 * 20.10.2022
 */
@Component
public class MeasurementGraphDrawer {

    public void drawChart(List<Measurement> measurements) {
        int size = measurements.size();
        double[] xData = new double[size];
        double[] yData = new double[size];

        for (int i = 0; i < size; i++) {
            xData[i] = i;
            yData[i] = measurements.get(i).getValue();
        }

        XYChart chart = QuickChart.getChart(
                "Temperature graph",
                "Measurement number",
                "Temperature",
                "Graph",
                xData,
                yData);

        new SwingWrapper(chart).displayChart();
    }
}
