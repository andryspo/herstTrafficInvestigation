package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.enums.Functions;
import com.okhrymovych_kalandyak.model.TrafficPoint;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {

    public void addTrafficPointsToChart(LineChart<Double, Double> lineChart, List<TrafficPoint> points, boolean isRaw, Functions function) {

        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        if (isRaw) {
            series.setName("Raw traffic data");
        } else if (function != null) {
            switch (function) {
                case SIN:
                    series.setName("func sin(x)");
                    break;
                case COS:
                    series.setName("func cos(x)");
                    break;
                case _2SIN:
                    series.setName("func 2*sin(x)");
                    break;
                case _2COS:
                    series.setName("func 2*cos(x)");
                    break;
                case SINCOS:
                    series.setName("func sin(x)*cos(x)");
                    break;
            }
        } else {
            series.setName("graphic method");
        }

        if(points != null) {
            points.forEach(
                    e -> series.getData().add(new XYChart.Data<>(e.getTime(), e.getSpeed()))
            );
        }

        lineChart.getData().add(series);
    }
}
