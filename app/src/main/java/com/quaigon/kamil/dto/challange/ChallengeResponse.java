package com.quaigon.kamil.dto.challange;

public class ChallengeResponse {
    private String status;
    private int challenge;
    private int game;

    public ChallengeResponse(String status, int challenge, int game) {
        this.status = status;
        this.challenge = challenge;
        this.game = game;
    }

    public String getStatus() {
        return status;
    }

    public int getChallenge() {
        return challenge;
    }

    public int getGame() {
        return game;
    }
}
