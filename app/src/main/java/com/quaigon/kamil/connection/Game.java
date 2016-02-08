package com.quaigon.kamil.connection;


public class Game {
    private String id;
    private String sgf;

    public Game(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public String getSgf() {
        return sgf;
    }
}
