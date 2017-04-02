package com.guideapp.utilities;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.guideapp.R;

public class ViewUtil {
    public static void showViewLayout(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(
                context, R.anim.abc_slide_out_bottom);
        animation.setDuration(200);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);

        animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
        animation.setDuration(400);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }
}
