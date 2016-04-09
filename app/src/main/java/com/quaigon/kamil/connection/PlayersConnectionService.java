package com.quaigon.kamil.connection;

import com.quaigon.kamil.dto.player.Friends;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayersConnectionService {
    @GET("/api/v1/me/friends")
    Call<Friends> getFriends();
}
