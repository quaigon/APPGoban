package com.quaigon.kamil.connection;

import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Field;

/**
 * Created by Kamil on 07.02.2016.
 */
public interface LoginService {
    @FormUrlEncoded
    @POST("oauth2/access_token")
    Call<AccessToken> getAccessToken (@Field("client_id") String clientid,
                                      @Field("client_secret") String clientSecret,
                                      @Field("username") String username,
                                      @Field("password") String password,
                                      @Field("grant_type") String grantType);
}
