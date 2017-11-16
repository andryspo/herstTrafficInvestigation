package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.services.interfaces.IFIleChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileChooserImpl implements IFIleChooser {

    private Logger logger = LoggerFactory.getLogger(FileChooserImpl.class);

    @Override
    public String choose(Window stage) {
        logger.info("choose file");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            logger.info("successfully selected file {}", selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        } else {
            logger.error("problem cannot select file");
            return null;
        }
    }
}
