package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.interfaces.ICSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVParser implements ICSVParser {

    private Logger logger = LoggerFactory.getLogger(CSVParser.class);

    public List<TrafficPoint> parse(String filePath) {

        assert !filePath.endsWith(".csv");

        String line = "";
        String cvsSplitBy = ",";
        List<TrafficPoint> trafficPoints = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            logger.info("read data from file " + filePath);

            //read first line it is header
            if (br.readLine() != null) {
                while ((line = br.readLine()) != null) {
                    String[] res = line.split(cvsSplitBy);
                    trafficPoints.add(new TrafficPoint(Double.parseDouble(res[1]), Double.parseDouble(res[0])));
                }
            }

        } catch (IOException e) {
            logger.error("problem with reading from file: " + filePath);
        }

        return trafficPoints;
    }

}