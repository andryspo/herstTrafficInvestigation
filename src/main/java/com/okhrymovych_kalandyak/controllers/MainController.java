package com.okhrymovych_kalandyak.controllers;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.interfaces.ICSVParser;
import com.okhrymovych_kalandyak.services.interfaces.IHerstCalculator;
import com.okhrymovych_kalandyak.validator.AParameterValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @Autowired
    private ICSVParser csvParser;

    private Logger logger = LoggerFactory.getLogger("MainController");

    @Autowired
    private IHerstCalculator herstCalculator;

    @Autowired
    private AParameterValidator aParameterValidator;

    public void calc(ActionEvent actionEvent) {

        List<TrafficPoint> trafficPoints = csvParser.parse(fileTF.getText());

        if(!aParameterValidator.validate(aParamTF.getText())) {
            return;
        }

        double a = Double.parseDouble(aParamTF.getText());

        if (trafficPoints != null && !trafficPoints.isEmpty()) {
            resultTF.setText(String.valueOf(herstCalculator.calc(trafficPoints, a)));
        }
    }
}


