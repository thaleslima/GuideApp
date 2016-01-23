package com.guideapp.guideapp.UI.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.adapters.LocalDetailAdapter;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.model.LocalDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocalDetailActivity extends BaseActivity implements RecyclerViewItemClickListener {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private LocalDetailAdapter mAdapter;
    private List<LocalDetail> mDataSet;
    private ImageView mImage;
    private static final String EXTRA_IMAGE = "teste";

    public static void navigate(Activity activity, View transitionImage) {
        Intent intent = new Intent(activity, LocalDetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initActivityTransitions();
        setContentView(R.layout.activity_local_detail);

        //ViewCompat.setTransitionName(findViewById(R.id.appBarLayout), EXTRA_IMAGE);
        //supportPostponeEnterTransition();

        initToolbar();
        setFindViewById();
        setViewProperties();
    }

    private void initToolbar() {
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
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mImage = (ImageView) findViewById(R.id.image);
    }

    private void setViewProperties() {
        mDataSet = new ArrayList<>();
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante Urbano", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_language_black_24dp,
                "http://www.pousadariogrande.com.br", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_call_black_24dp,
                "35 3525-1256", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_apps_black_24dp,
                "Wifi, Ar condicionado", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Av. Torquato José de Almeida, 790 - Centro", false,
                LocalDetailAdapter.LOCAL_DETAIL));


        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_MAP));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_TITLE_OPINION));

        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp,
                "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));

        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        //mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
        // getColor(android.R.color.white));

        mAdapter = new LocalDetailAdapter(this, this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        //supportStartPostponedEnterTransition();

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalPhotoActivity.navigate(LocalDetailActivity.this);
            }
        });

        Picasso.with(this).load("http://lorempixel.com/500/500/animals/1").into(mImage,
                new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //supportStartPostponedEnterTransition();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
