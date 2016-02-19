package com.quaigon.kamil.connection;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.quaigon.kamil.pojo.AccessToken;


public class AuthenticationRepositoryImp implements AuthenticationRepository {

    public static final String PREFS_NAME = "Prefs";
    @Inject
    SharedPreferences settings;

    @Override
    public AccessToken loadAccessToken() {
        String accessToken = settings.getString("accessToken", null );
        String scope = settings.getString("scope", null);
        long expiresIn = settings.getLong("expiresIn", 0);
        String refreshToken = settings.getString("refreshToken", null);

        return new AccessToken(accessToken,scope,expiresIn,refreshToken);
    }

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accessToken", accessToken.getAccess_token());
        editor.putString("scope", accessToken.getScope());
        editor.putLong("expiresIn", accessToken.getExpires_in());
        editor.putString("refreshToken", accessToken.getRefresh_token());

        editor.commit();
    }
}
