package com.guideapp.guideapp.ui.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.guideapp.guideapp.ui.infrastructure.CommonUtils;

public class ScrollingButtonsBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private int toolbarHeight;

    private int mToolbarHeight;

    public ScrollingButtonsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mToolbarHeight = CommonUtils.getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child,
                                          View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)
                    child.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = child.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) mToolbarHeight;
            child.setTranslationY(-distanceToScroll * ratio);
        }

        return true;
    }
}


