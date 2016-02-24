package com.quaigon.kamil.connection;


import com.quaigon.kamil.pojo.AccessToken;

public interface AuthenticationRepository {

    AccessToken loadAccessToken();

    void saveAccessToken(AccessToken accessToken);


}
