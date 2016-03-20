package com.quaigon.kamil.goban.gametree;

public class Move {
    int x;
    int y;
    int color;

    public Move(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }
}
