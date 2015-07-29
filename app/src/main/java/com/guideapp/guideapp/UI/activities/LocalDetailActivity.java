package com.guideapp.guideapp.UI.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import com.squareup.picasso.Callback;

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
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initActivityTransitions();
        setContentView(R.layout.activity_local_detail);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFindViewById();
        setViewProperties();
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
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mImage = (ImageView) findViewById(R.id.image);
    }

    private void setViewProperties() {
        mDataSet = new ArrayList<>();
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", false, LocalDetailAdapter.LOCAL_DETAIL));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_MAP));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_TITLE_OPINION));

        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));
        mDataSet.add(new LocalDetail(R.drawable.ic_location_on_black_24dp, "Restarautante", true, LocalDetailAdapter.LOCAL_DETAIL_OPINION));


        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        mAdapter = new LocalDetailAdapter(this, this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));


        Picasso.with(this).load("http://lorempixel.com/500/500/animals/1").into(mImage, new Callback() {
            @Override public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override public void onError() {

            }
        });
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        supportStartPostponedEnterTransition();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
