package com.quaigon.kamil.connection;


import com.quaigon.kamil.dto.token.AccessToken;

public interface AuthenticationRepository {

    AccessToken loadAccessToken();

    void saveAccessToken(AccessToken accessToken);


}
