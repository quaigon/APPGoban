package com.quaigon.kamil;

import android.app.Application;
import android.content.Context;

import roboguice.RoboGuice;

/**
 * Created by Kamil on 24.02.2016.
 */
public class GobanApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RoboGuice.setUseAnnotationDatabases(false);
    }
}
