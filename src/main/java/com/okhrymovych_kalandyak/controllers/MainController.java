package com.okhrymovych_kalandyak.controllers;

import com.okhrymovych_kalandyak.enums.Functions;
import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.Modulator;
import com.okhrymovych_kalandyak.services.interfaces.ICSVParser;
import com.okhrymovych_kalandyak.services.interfaces.IHerstCalculator;
import com.okhrymovych_kalandyak.validator.AParameterValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MainController {

    @FXML
    private TextField fileTF;

    @FXML
    private TextField resultTF;

    @FXML
    private TextField aParamTF;

    @FXML
    private ComboBox<Functions> functionCB;

    @FXML
    private LineChart<Double, Double> chartLC;

    @FXML
    private LineChart<Double, Double> trafficChartLC;

    @FXML
    private TextField nTF;

    @FXML
    private TextField minTF;

    @FXML
    private TextField maxTF;

    @FXML
    private TextField dispersionTF;

    @FXML
    private TextField aModulateParamTF;

    @FXML
    private TextField modulatedHerstParamTF;

    @Autowired
    private ICSVParser csvParser;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private IHerstCalculator herstCalculator;

    @Autowired
    private AParameterValidator aParameterValidator;

    @Autowired
    private Modulator modulator;

    @FXML
    public void initialize() {
        functionCB.setItems(FXCollections.observableArrayList(
                Functions.SIN,
                Functions.COS,
                Functions._2COS,
                Functions._2SIN,
                Functions.SINCOS
        ));

        functionCB.setValue(Functions.SIN);
    }

    public void calc(ActionEvent actionEvent) {

        List<TrafficPoint> trafficPoints = csvParser.parse(fileTF.getText());

        if (!aParameterValidator.validate(aParamTF.getText())) {
            return;
        }

        double a = Double.parseDouble(aParamTF.getText());

        addTrafficPointsToChart(trafficChartLC, trafficPoints, true);

        if (trafficPoints != null && !trafficPoints.isEmpty()) {
            resultTF.setText(String.valueOf(herstCalculator.calc(trafficPoints, a)));
        }
    }

    public void modulate(ActionEvent actionEvent) {

        int n = Integer.parseInt(nTF.getText());
        double min = Double.parseDouble(minTF.getText());
        double max = Double.parseDouble(maxTF.getText());
        Functions functions = functionCB.getValue();
        double dispersion = Double.parseDouble(dispersionTF.getText());
        double aParam = Double.parseDouble(aModulateParamTF.getText());

        List<TrafficPoint> modulated = modulator.modulate(min, max, n, functions, dispersion);
        addTrafficPointsToChart(chartLC, modulated, false);
        modulatedHerstParamTF.setText(String.valueOf(herstCalculator.calc(modulated, aParam)));
    }

    private void addTrafficPointsToChart(LineChart<Double, Double> lineChart, List<TrafficPoint> points, boolean isRaw) {

        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        if(isRaw) {
            series.setName("Raw traffic data");
        } else {
            switch (functionCB.getValue()) {
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
        }

        points.forEach(
                e -> series.getData().add(new XYChart.Data<>(e.getTime(), e.getSpeed()))
        );

        lineChart.getData().add(series);
    }

    public void clearReal(ActionEvent actionEvent) {
        trafficChartLC.getData().clear();
    }

    public void clearModulated(ActionEvent actionEvent) {
        chartLC.getData().clear();
    }
}


