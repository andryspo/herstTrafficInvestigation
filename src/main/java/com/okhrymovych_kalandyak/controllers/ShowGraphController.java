package com.okhrymovych_kalandyak.controllers;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.ChartService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ShowGraphController {

    private Logger logger = LoggerFactory.getLogger(ShowGraphController.class);

    @FXML
    private LineChart<Double, Double> graphicsLC;

    @FXML
    private Label slopLB;

    @Autowired
    private ChartService chartService;

    public void show(List<TrafficPoint> trafficPoint, double slope) {
        logger.info("show graphics");
        chartService.addTrafficPointsToChart(graphicsLC, trafficPoint, false, null);
        slopLB.setText(slopLB.getText() + " " + slope);
    }

}
