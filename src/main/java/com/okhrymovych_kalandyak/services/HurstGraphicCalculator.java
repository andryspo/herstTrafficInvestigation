package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.interfaces.IHerstCalculator;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HurstGraphicCalculator implements IHerstCalculator {

    private Logger logger = LoggerFactory.getLogger(HurstGraphicCalculator.class);

    private double slope;

    @Override
    public double calc(List<TrafficPoint> trafficPoints, double m) {

        SimpleRegression simpleRegression = new SimpleRegression();
        List<TrafficPoint> points = new ArrayList<>();

        for (int i = 1; i <= m; i++) {
            points.add(
                    new TrafficPoint(
                            Math.log10(i), Math.log10(varxjm(xjm(trafficPoints, i), trafficPoints, (int) m))));
        }

        points.forEach(e -> simpleRegression.addData(e.getTime(), e.getSpeed()));
        slope = simpleRegression.getSlope();
        logger.info("slope {}", slope);

        return 1 - (slope / 2);
    }

    public List<Double> xjm(List<TrafficPoint> trafficPoints, int m) {
        List<Double> xjm = new ArrayList<>();
        for (int i = 0; i < trafficPoints.size() / m; i++) {
            double current = 0;

            for (int j = i * m; j < (i + 1) * m; j++) {
                current += trafficPoints.get(j).getSpeed();
            }
            current /= m;
            xjm.add(current);
        }

        return xjm;
    }

    public double varxjm(List<Double> xm, List<TrafficPoint> trafficPoints, int m) {
        double varxm = 0;

        final double ser = calcSeredneArufmetuchne(trafficPoints
                .stream()
                .map(TrafficPoint::getSpeed)
                .collect(Collectors.toList()));

        for (Double aXm : xm) {
            varxm += (aXm - ser) * (aXm - ser);
        }

        varxm = 1.0 / (xm.size() * varxm);
        //varxm = 1.0 / varxm;
        logger.info("varxjm: {}", varxm);

        return varxm;
    }

    public List<TrafficPoint> getLog(List<TrafficPoint> trafficPoints, double m) {

        List<TrafficPoint> logs = new ArrayList<>();
        for (int i = 1; i <= m; i++) {
            logs.add(
                    new TrafficPoint(
                            Math.log10(varxjm(xjm(trafficPoints, i), trafficPoints, (int) m)),
                            Math.log10(i)
                    ));
        }

        return logs;
    }

    public double getSlope() {
        return slope;
    }
}
