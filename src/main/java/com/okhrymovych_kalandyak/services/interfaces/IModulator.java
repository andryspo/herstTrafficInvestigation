package com.okhrymovych_kalandyak.services.interfaces;

import com.okhrymovych_kalandyak.enums.Functions;
import com.okhrymovych_kalandyak.model.TrafficPoint;

import java.util.List;

public interface IModulator {

    List<TrafficPoint> modulate(double minValue, double maxValue, int n, Functions function, double dispertion);

}
