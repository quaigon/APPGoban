package com.quaigon.kamil.goban;

/**
 * Created by Kamil on 06.03.2016.
 */
public class StateContainer {
    public StateContainer(Goban goban, String comment) {
        this.goban = goban;
        this.comment = comment;
    }

    private Goban goban;
    private String comment;

    public Goban getGoban() {
        return goban;
    }

    public String getComment() {
        return comment;
    }
}
