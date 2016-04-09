package com.quaigon.kamil.dto.player;

/**
 * Created by Kamil on 19.02.2016.
 */
public class Player {
    private long id;
    private String username;
    private String country;
    private String rating;
    private String rating_blitz;
    private String rating_live;
    private String rating_correspondence;
    private String ranking;


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCountry() {
        return country;
    }

    public String getRating() {
        return rating;
    }

    public String getRanking() {
        return ranking;
    }
}
