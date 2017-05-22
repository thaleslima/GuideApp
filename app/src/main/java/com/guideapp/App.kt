package com.guideapp

import android.support.multidex.MultiDexApplication

import com.facebook.stetho.Stetho
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}