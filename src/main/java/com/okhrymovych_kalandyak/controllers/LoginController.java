package com.okhrymovych_kalandyak.controllers;

import com.okhrymovych_kalandyak.constants.Token;
import com.okhrymovych_kalandyak.dto.User;
import com.okhrymovych_kalandyak.services.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private TextField logInTF;

    @FXML
    private TextField passwordPF;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    public void signIn(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setUserName(logInTF.getText());
        user.setPassword(passwordPF.getText());

        logger.info("user login: " + user.getUserName());
        logger.info("user password: " + user.getPassword());

        String token;

        try {
            token = authenticationService.auth(user);
            Token.TOKEN = token;
        } catch (Exception e) {
            logger.error("cannot authenticate");

            return;
        }


        logger.info("auth: " + token);
        logger.info("authentication successful");
        openMainScene();

    }

    private void openMainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) logInTF.getScene().getWindow();;
        stage.setScene(scene);
        stage.getIcons().add(new Image("/ico/ico.png"));
        stage.show();
    }
}
