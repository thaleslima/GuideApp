package com.guideapp.guideapp.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.guideapp.guideapp.R;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    /**
     * Theme
     * @param resid Id theme
     * @param residStatusBarColor Id status bar color
     */
    public void setTheme(int resid, int residStatusBarColor) {
        super.setTheme(resid);

        setStatusBarColor(residStatusBarColor);
    }
}
