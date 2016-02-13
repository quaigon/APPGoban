package com.quaigon.kamil.activities;


public class Game {
    private long id;
    private String sgf;

    public Game(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public String getSgf() {
        return sgf;
    }
}
