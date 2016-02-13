package com.quaigon.kamil.connection;


public interface AuthenticationRepository {

    AccessToken loadAccessToken();
    void saveAccessToken (AccessToken accessToken);


}
