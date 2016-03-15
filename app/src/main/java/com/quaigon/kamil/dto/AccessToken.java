package com.quaigon.kamil.dto;

/**
 * Created by Kamil on 07.02.2016.
 */
public class AccessToken {

    public String access_token;
    public String scope;
    public long expires_in;
    public String refresh_token;

    public AccessToken(String access_token, String scope, long expires_in, String refresh_token) {
        this.access_token = access_token;
        this.scope = scope;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

}
