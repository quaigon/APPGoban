package com.quaigon.kamil.dto.gamelist;


import com.quaigon.kamil.dto.player.Players;

public class Game {
    private Players players;
    private long id;
    private String sgf;
    private int width;
    private boolean annulled;
    private String started;
    private String ended;

    public Game(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public String getSgf() {
        return sgf;
    }

    public int getWidth() {
        return width;
    }

    public boolean isAnnulled() {
        return annulled;
    }


    public String getEnded() {
        return ended;
    }

    public String getStarted() {
        return started;
    }

    public Players getPlayers() {
        return players;
    }
}
