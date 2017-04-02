package com.guideapp;

import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

public class App extends MultiDexApplication {

    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
