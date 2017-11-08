package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.enums.Functions;
import com.okhrymovych_kalandyak.model.TrafficPoint;
import com.okhrymovych_kalandyak.services.interfaces.IModulator;
import com.okhrymovych_kalandyak.services.interfaces.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Modulator implements IModulator {

    private Logger logger = LoggerFactory.getLogger(Modulator.class);

    @Autowired
    private RandomService randomService;

    @Override
    public List<TrafficPoint> modulate(double minValue, double maxValue, int n, Functions function, double dispersion) {
        List<TrafficPoint> trafficPoints = new ArrayList<>();

        double dx = (maxValue - minValue) / n;
        logger.info("dx {}", dx);
        double current = 0;

        for (int i = 0; i < n; i++) {

            switch (function) {
                case SIN:
                    trafficPoints.add(new TrafficPoint(randomService.percent(
                            Math.sin(current), dispersion) + 1.0 + dispersion / 100, current));
                    break;
                case COS:
                    trafficPoints.add(new TrafficPoint(randomService.percent(
                            Math.cos(current), dispersion) + 1.0 + dispersion / 100, current));
                    break;
                case _2SIN:
                    trafficPoints.add(new TrafficPoint(randomService.percent(
                            2 * Math.sin(current), dispersion) + 2.0 + dispersion / 100, current));
                    break;
                case _2COS:
                    trafficPoints.add(new TrafficPoint(randomService.percent(
                            2 * Math.cos(current), dispersion) + 2.0 + dispersion / 100, current));
                    break;
                case SINCOS:
                    trafficPoints.add(new TrafficPoint(randomService.percent(
                            Math.sin(current) * Math.cos(current), dispersion) + 0.5 + dispersion / 100, current));
                    break;
            }

            current += dx;
        }

        return trafficPoints;
    }

}
