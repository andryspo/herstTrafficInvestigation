package com.okhrymovych_kalandyak.services.interfaces;

import com.okhrymovych_kalandyak.model.TrafficPoint;

import java.util.List;

public interface IHerstCalculator {

    double calc(List<TrafficPoint> trafficPoints, double a);

}
