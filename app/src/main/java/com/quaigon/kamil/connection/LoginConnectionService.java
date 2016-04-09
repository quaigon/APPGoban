package com.quaigon.kamil.connection;

import com.quaigon.kamil.dto.token.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Kamil on 25.03.2016.
 */
public interface LoginConnectionService {
    @FormUrlEncoded
    @POST("oauth2/access_token")
    Call<AccessToken> getAccessToken(@Field("client_id") String clientid,
                                     @Field("client_secret") String clientSecret,
                                     @Field("username") String username,
                                     @Field("password") String password,
                                     @Field("grant_type") String grantType);
}
