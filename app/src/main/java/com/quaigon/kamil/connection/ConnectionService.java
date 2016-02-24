package com.quaigon.kamil.connection;

import com.quaigon.kamil.pojo.AccessToken;
import com.quaigon.kamil.pojo.Games;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ConnectionService {
    @FormUrlEncoded
    @POST("oauth2/access_token")
    Call<AccessToken> getAccessToken(@Field("client_id") String clientid,
                                     @Field("client_secret") String clientSecret,
                                     @Field("username") String username,
                                     @Field("password") String password,
                                     @Field("grant_type") String grantType);


    @GET("/api/v1/me/games")
    Call<Games> getGames();


    @GET("/api/v1/games/{id}/sgf/")
    Call<String> getSgf(@Path("id") long id);


}
