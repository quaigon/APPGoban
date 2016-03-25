package com.quaigon.kamil.goban.gobanlogic;


public class Stone {
    private int color;

    public Stone(int color) {
        this.color = color;

    }

    public int getColor() {
        return color;
    }


    @Override
    public String toString() {
        return "" + color;
    }
}
