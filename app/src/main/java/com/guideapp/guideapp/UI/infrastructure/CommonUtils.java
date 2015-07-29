package com.guideapp.guideapp.UI.infrastructure;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.guideapp.guideapp.R;

/**
 * Created by thales on 7/28/15.
 */
public class CommonUtils {
    public static void dismissViewLayout(Context context, View view) {
        if (view.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
            animation.setDuration(600);
            view.startAnimation(animation);
            view.setVisibility(View.GONE);
        }
    }

    public static void showViewLayout(Context context, View view) {
        if (view.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            animation.setDuration(600);
            view.startAnimation(animation);
            view.setVisibility(View.VISIBLE);
        }
    }
}
