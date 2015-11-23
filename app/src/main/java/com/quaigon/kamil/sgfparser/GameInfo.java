package com.quaigon.kamil.sgfparser;

public class GameInfo {
    private String gameName;
    private String whiteName;
    private String blackName;
    private String date;
    private String rules;
    private String result;
    private int time;
    private int handicap;

    public GameInfo(String gameName, String whiteName, String blackName, String date, String rules, String result, int time, int handicap) {
        this.gameName = gameName;
        this.whiteName = whiteName;
        this.blackName = blackName;
        this.date = date;
        this.rules = rules;
        this.result = result;
        this.time = time;
        this.handicap = handicap;
    }
}
