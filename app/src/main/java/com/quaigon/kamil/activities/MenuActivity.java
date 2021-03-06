package com.quaigon.kamil.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.quaigon.kamil.goban.view.GobanActivity;
import com.quaigon.kamil.goban.R;
import com.quaigon.kamil.dto.token.AccessToken;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MenuActivity extends RoboActivity {

    @InjectView(R.id.openGameButton)
    private Button openGameButton;

    @InjectView(R.id.getGamesButton)
    private Button getGamesButon;

    @InjectView(R.id.getFriendListButton)
    private Button getFriendListButton;

    private static final int FILE_SELECT_CODE = 10;

    private AccessToken token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        openGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("file/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Wybierz SGF"), 1000);
            }
        });


        getGamesButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GamesListActivity.class);
                startActivity(intent);
            }
        });

        getFriendListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PlayerListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            Uri uri = data.getData();
            Intent intent = new Intent(MenuActivity.this, GobanActivity.class);
            intent.putExtra("sgfPath", uri.getPath());
            startActivity(intent);
        }
    }
}
