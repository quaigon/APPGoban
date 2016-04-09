package com.quaigon.kamil.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.quaigon.kamil.goban.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class PlayerActivity extends RoboActivity {

    @InjectView(R.id.playerName)
    TextView usernameTextView;

    @InjectExtra(value = "id", optional = false)
    private long id;

    @InjectExtra(value = "username", optional = false)
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        usernameTextView.setText(username);
    }
}
