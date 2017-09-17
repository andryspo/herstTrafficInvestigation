package com.okhrymovych_kalandyak.controllers;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.CSVParser;
import com.okhrymovych_kalandyak.services.HerstCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MainController {

    @FXML
    private TextField fileTF;

    @FXML
    private TextField resultTF;

    @Autowired
    private CSVParser csvParser;

    @Autowired
    private HerstCalculator herstCalculator;

    public void calc(ActionEvent actionEvent) {

        List<TrafficPoint> trafficPoints = csvParser.parse(fileTF.getText());
        if (trafficPoints != null && !trafficPoints.isEmpty()) {
            resultTF.setText(String.valueOf(herstCalculator.calc(trafficPoints)));
        }

    }
}


