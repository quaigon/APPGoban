package com.quaigon.kamil.connection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quaigon.kamil.goban.R;

import roboguice.activity.RoboListActivity;

public class GamesListActivity extends RoboListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
    }
}
