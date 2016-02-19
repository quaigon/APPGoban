package com.quaigon.kamil.pojo;


public class Game {
    private Players players;
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

    public Players getPlayers() {
        return players;
    }
}
