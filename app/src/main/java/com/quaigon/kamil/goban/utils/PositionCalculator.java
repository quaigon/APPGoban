package com.quaigon.kamil.goban.utils;

import roboguice.util.Ln;

/**
 * Created by Kamil on 11.03.2016.
 */
public class PositionCalculator {
    private float screenWidth;
    private float squareWidth;
    private float squareHeight;
    private float stoneRadius;


    public PositionCalculator(float screenWidth) {
        this.screenWidth = screenWidth;
        this.squareWidth = Math.round(screenWidth /20);
        this.squareHeight = squareWidth;

    }


    public float xToPixel(int x) {
        return squareWidth + x*squareWidth;
    }

    public float yToPixel(int y) {
        return squareHeight + y*squareHeight;
    }

    public int fromPixelToX (float xPixel) {
        float newx = (xPixel - squareWidth)/squareWidth;

        int res = (int) Math.round(newx)+1;

        if (res <= 0) return 1;
        else if (res >= 20) return 19;
        return res;
    }


}
