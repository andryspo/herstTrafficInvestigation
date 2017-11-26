package com.okhrymovych_kalandyak.controllers;

import com.okhrymovych_kalandyak.enums.Functions;
import com.okhrymovych_kalandyak.enums.HurstCalculationType;
import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.ChartService;
import com.okhrymovych_kalandyak.services.FileChooserImpl;
import com.okhrymovych_kalandyak.services.HurstGraphicCalculator;
import com.okhrymovych_kalandyak.services.Modulator;
import com.okhrymovych_kalandyak.services.interfaces.ICSVParser;
import com.okhrymovych_kalandyak.services.interfaces.IHerstCalculator;
import com.okhrymovych_kalandyak.validator.AParameterValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
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
    private ComboBox<HurstCalculationType> hurstCalculationTypeCB;

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

    @FXML
    private TextField mModulateParamTF;

    @FXML
    private CheckBox drawCB;

    @FXML
    private ComboBox<HurstCalculationType> modulatedMethodCB;

    @FXML
    private TextField mTF;

    @Autowired
    private ICSVParser csvParser;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @Qualifier("hurstRSCalculator")
    @Autowired
    private IHerstCalculator herstCalculatorRS;

    @Qualifier("hurstGraphicCalculator")
    @Autowired
    private HurstGraphicCalculator herstCalculatorGraphic;

    @Autowired
    private AParameterValidator aParameterValidator;

    @Autowired
    private Modulator modulator;

    @Autowired
    private FileChooserImpl fileChooser;

    @Autowired
    private ChartService chartService;

    @Autowired
    private ShowGraphController showGraphController;

    private List<TrafficPoint> points;

    @FXML
    public void initialize() {

        hurstCalculationTypeCB.setItems(FXCollections.observableArrayList(
                HurstCalculationType.GRAPHIC,
                HurstCalculationType.RS
        ));

        modulatedMethodCB.setItems(FXCollections.observableArrayList(
                HurstCalculationType.GRAPHIC,
                HurstCalculationType.RS
        ));

        modulatedMethodCB.setValue(HurstCalculationType.RS);

        hurstCalculationTypeCB.setValue(HurstCalculationType.RS);

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

        chartService.addTrafficPointsToChart(trafficChartLC, trafficPoints, true, null);

        if (trafficPoints != null && !trafficPoints.isEmpty()) {
            switch (hurstCalculationTypeCB.getValue()) {
                case RS:
                    resultTF.setText(String.valueOf(herstCalculatorRS.calc(trafficPoints, a)));
                    break;
                case GRAPHIC:
                    double m = Double.parseDouble(mTF.getText());
                    points = herstCalculatorGraphic.getLog(trafficPoints, m);
                    resultTF.setText(String.valueOf(herstCalculatorGraphic.calc(trafficPoints, m)));
                    break;
            }

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

        chartService.addTrafficPointsToChart(chartLC, modulated, false, functions);

        switch (modulatedMethodCB.getValue()) {
            case RS:
                modulatedHerstParamTF.setText(String.valueOf(herstCalculatorRS.calc(modulated, aParam)));
                break;
            case GRAPHIC:
                points = herstCalculatorGraphic.getLog(modulated, Double.parseDouble(mModulateParamTF.getText()));
                modulatedHerstParamTF.setText(String.valueOf(herstCalculatorGraphic.calc(modulated,
                        Double.parseDouble(mModulateParamTF.getText()))));
                break;
        }
    }

    public void clearReal(ActionEvent actionEvent) {
        trafficChartLC.getData().clear();
    }

    public void clearModulated(ActionEvent actionEvent) {
        chartLC.getData().clear();
    }

    public void searchFile(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window stage = source.getScene().getWindow();
        String path = fileChooser.choose(stage);
        if (path != null && !path.isEmpty()) {
            fileTF.setText(path);
        }
    }

    public void showGraph(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/showGraph.fxml"));
            fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image("/ico/ico.png"));
            stage.show();
            showGraphController.show(points, herstCalculatorGraphic.getSlope());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modulatedChowGraph(ActionEvent actionEvent) {
        showGraph(actionEvent);
    }
}


