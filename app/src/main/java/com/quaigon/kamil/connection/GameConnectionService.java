package com.quaigon.kamil.connection;

import com.quaigon.kamil.dto.gamelist.MoveContainer;
import com.quaigon.kamil.dto.gamelist.Response;
import com.quaigon.kamil.dto.gamelist.Games;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kamil on 25.03.2016.
 */
public interface GameConnectionService {
    @GET("/api/v1/me/games")
    Call<Games> getGames(@Query("page") int page);

    @GET("/api/v1/games/{id}/sgf/")
    Call<String> getSgf(@Path("id") long id);


    @FormUrlEncoded
    @POST("http://online-go.com/v1/games/{id}/move/")
    Call<Response> postMove(@Path("id") long id, @Body MoveContainer move);

}
