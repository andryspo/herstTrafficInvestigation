package com.okhrymovych_kalandyak.model;

public class TrafficPoint {

    private double speed;

    private double time;

    public TrafficPoint(double speed, double time) {
        this.speed = speed;
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
