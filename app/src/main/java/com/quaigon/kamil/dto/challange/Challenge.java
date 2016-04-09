package com.quaigon.kamil.dto.challange;

import com.quaigon.kamil.dto.challange.timecontrol.FisherParameters;

public class Challenge {
    private GameInfo game;
    private String challenger_color;
    private int min_ranking;
    private int max_ranking;


    public Challenge(GameInfo game, String challenger_color, int min_ranking, int max_ranking) {
        this.game = game;
        this.challenger_color = challenger_color;
        this.min_ranking = min_ranking;
        this.max_ranking = max_ranking;
    }

    public GameInfo getGame() {
        return game;
    }

    public String getChallenger_color() {
        return challenger_color;
    }

    public int getMin_ranking() {
        return min_ranking;
    }

    public int getMax_ranking() {
        return max_ranking;
    }
}
