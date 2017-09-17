package com.okhrymovych_kalandyak.services.interfaces;

import com.okhrymovych_kalandyak.model.TrafficPoint;

import java.util.List;

public interface ICSVParser {

    List<TrafficPoint> parse(String filePath);

}
