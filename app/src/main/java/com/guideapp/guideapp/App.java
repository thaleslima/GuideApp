package com.guideapp.guideapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class App extends Application {

    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
