package com.quaigon.kamil.connection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.quaigon.kamil.goban.GobanActivity;
import com.quaigon.kamil.goban.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class MenuActivity extends RoboActivity {

    @InjectView(R.id.openGameButton)
    private Button openGameButton;

    @InjectView(R.id.getGamesButton)
    private Button getGamesButon;

    @InjectExtra("accesstoken")
    private String accessToken;

    @InjectExtra("refreshtoken")
    private String refreshToken;

    @InjectExtra("scope")
    private String scope;

    @InjectExtra("expiresin")
    private long expiresIn;


    private AccessToken token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.token = new AccessToken(accessToken,scope,expiresIn,refreshToken);

        this.openGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GobanActivity.class);
                startActivity(intent);
            }
        });


        this.getGamesButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
}
