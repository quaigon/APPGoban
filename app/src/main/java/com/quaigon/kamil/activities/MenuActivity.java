package com.quaigon.kamil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.quaigon.kamil.pojo.AccessToken;
import com.quaigon.kamil.goban.GobanActivity;
import com.quaigon.kamil.goban.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MenuActivity extends RoboActivity {

    @InjectView(R.id.openGameButton)
    private Button openGameButton;

    @InjectView(R.id.getGamesButton)
    private Button getGamesButon;



    private AccessToken token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


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
                Intent intent = new Intent(MenuActivity.this, GamesListActivity.class);
                startActivity(intent);
            }
        });

    }




}
