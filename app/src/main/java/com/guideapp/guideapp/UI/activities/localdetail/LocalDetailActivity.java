package com.guideapp.guideapp.ui.activities.localdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.ui.activities.BaseActivity;
import com.guideapp.guideapp.ui.activities.LocalPhotoActivity;
import com.guideapp.guideapp.ui.adapters.LocalDetailAdapter;
import com.guideapp.guideapp.model.LocalDetail;
import java.util.List;

public class LocalDetailActivity extends BaseActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LocalDetailAdapter mAdapter;
    private List<LocalDetail> mDataSet;
    private ImageView mImage;
    private Local mLocal;
    private static final String EXTRA_IMAGE = "image";
    private static final String EXTRA_LOCAL = "local";

    public static void navigate(Activity activity, View transitionImage, Local local) {
        Intent intent = new Intent(activity, LocalDetailActivity.class);
        intent.putExtra(EXTRA_LOCAL, local);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, transitionImage, EXTRA_IMAGE);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalDetailActivity.class);
        context.startActivity(intent);
    }

    public static void navigate(Context context, Local local) {
        Intent intent = new Intent(context, LocalDetailActivity.class);
        intent.putExtra(EXTRA_LOCAL, local);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivityTransitions();
        setContentView(R.layout.activity_local_detail);

        initExtra();
        initToolbar();
        setFindViewById();
        setViewProperties();

        initFragment(LocalDetailFragment.newInstance(mLocal));
    }

    private void initFragment(Fragment detailFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }

    private void initExtra(){
        mLocal = getIntent().getParcelableExtra(EXTRA_LOCAL);
    }

    private void initToolbar() {
        ViewCompat.setTransitionName(findViewById(R.id.appBarLayout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);

            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void setFindViewById() {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsingToolbarLayout);
        mImage = (ImageView) findViewById(R.id.image);
    }

    private void setViewProperties() {
        mCollapsingToolbarLayout.setTitle(mLocal.getDescription());
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));

        supportStartPostponedEnterTransition();

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalPhotoActivity.navigate(LocalDetailActivity.this);
            }
        });


        Glide.with(this)
                .load(mLocal.getImagePath())
                .asBitmap()
                .into(new BitmapImageViewTarget(mImage) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);

                        mImage.setBackgroundResource(R.drawable.action_background_bottom);

                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }
                });
    }


    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.primary_dark);
        int primary = ContextCompat.getColor(this, R.color.primary);
        mCollapsingToolbarLayout.setContentScrimColor(palette.getVibrantColor(primary));
        mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
        supportStartPostponedEnterTransition();
    }
}
