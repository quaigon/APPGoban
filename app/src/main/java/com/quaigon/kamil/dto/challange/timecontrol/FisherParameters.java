package com.quaigon.kamil.dto.challange.timecontrol;

public class FisherParameters {
    private String time_control;
    private int initial_time;
    private int max_time;
    private int time_increment;

    public FisherParameters(int initial_time, int max_time, int time_increment) {
        this.time_control = "fischer";
        this.initial_time = initial_time;
        this.max_time = max_time;
        this.time_increment = time_increment;
    }
}
