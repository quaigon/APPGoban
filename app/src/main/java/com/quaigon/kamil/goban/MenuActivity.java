package com.quaigon.kamil.goban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MenuActivity extends RoboActivity {

    @InjectView(R.id.openGameButton)
    private Button openGameButton;



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


    }
}
