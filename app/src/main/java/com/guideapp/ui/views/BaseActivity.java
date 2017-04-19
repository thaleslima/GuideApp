package com.guideapp.ui.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sendCurrentScreen(this, getClass().getSimpleName());
    }

//    public static void sendCurrentScreen(@NonNull Activity activity, String name) {
//        Bundle params = new Bundle();
//        params.putString(FirebaseAnalytics.Param.VALUE, name);
//        FirebaseAnalytics.getInstance(activity).logEvent(Constants.Analytics.SCREEN, params);
//    }
}
