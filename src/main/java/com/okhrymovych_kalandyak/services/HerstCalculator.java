package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.interfaces.IHerstCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class HerstCalculator implements IHerstCalculator {

    private final double A = 1;

    private Logger logger = LoggerFactory.getLogger(HerstCalculator.class);

    //formula : H = log(R/S)/log(a*N)
    public double calc(List<TrafficPoint> trafficPoints) {

        logger.info("try to calc herst parameter");

        List<Double> xs = trafficPoints
                .stream()
                .map(TrafficPoint::getSpeed)
                .collect(Collectors.toList());
        logger.info("calc average");
        double average = calcSeredneArufmetuchne(xs);
        logger.info("calc S");
        double S = calcS(xs, average);
        logger.info("calc R");
        double R = calcR(xs, average);

        return Math.log(R / S) / Math.log(A * xs.size());
    }

    private double calcS(List<Double> trafficPoints, double average) throws NoSuchElementException {


        return Math.sqrt(trafficPoints
                .stream()
                .mapToDouble(e -> (e - average) * (e - average))
                .average()
                .getAsDouble());
    }

    private double calcSeredneArufmetuchne(List<Double> trafficPoints) {
        return trafficPoints.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .getAsDouble();
    }

    private double calcR(List<Double> trafficPoints, double xsr) {
        List<Double> zUs = new ArrayList<>();

        for (int i = 0; i < trafficPoints.size(); i++) {
            double currentZu = 0;
            for (int j = 0; j < i; j++) {
                currentZu += trafficPoints.get(j) - xsr;
            }
            zUs.add(currentZu);
        }

        double maxZu = zUs
                .stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .getAsDouble();

        double minZu = zUs
                .stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .getAsDouble();

        return maxZu - minZu;
    }
}