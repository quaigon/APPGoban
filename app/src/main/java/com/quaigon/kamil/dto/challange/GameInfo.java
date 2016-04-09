package com.quaigon.kamil.dto.challange;

import com.quaigon.kamil.dto.challange.timecontrol.FisherParameters;

public class GameInfo {
    private String name;
    private String rules;
    private boolean ranked;
    private int handicap;
    private FisherParameters time_control_parameters;
    private boolean pause_on_weekends;
    private int width;
    private int height;
    private boolean disable_analysis;

    public GameInfo(String name, String rules, boolean ranked, int handicap,
                    FisherParameters time_control_parameters, boolean pause_on_weekends,
                    int width, int height, boolean disable_analysis) {
        this.name = name;
        this.rules = rules;
        this.ranked = ranked;
        this.handicap = handicap;
        this.time_control_parameters = time_control_parameters;
        this.pause_on_weekends = pause_on_weekends;
        this.width = width;
        this.height = height;
        this.disable_analysis = disable_analysis;
    }

    public String getName() {
        return name;
    }

    public String getRules() {
        return rules;
    }

    public boolean isRanked() {
        return ranked;
    }

    public int getHandicap() {
        return handicap;
    }

    public FisherParameters getTime_control_parameters() {
        return time_control_parameters;
    }

    public boolean isPause_on_weekends() {
        return pause_on_weekends;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDisable_analysis() {
        return disable_analysis;
    }
}
