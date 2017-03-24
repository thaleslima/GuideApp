package com.guideapp.guideapp.ui.views.localdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.LocalDetail;
import com.guideapp.guideapp.ui.views.local.LocalActivity;
import com.guideapp.guideapp.utilities.Utility;

import java.util.List;

public class LocalDetailFragment extends Fragment implements LocalDetailContract.View {
    private static final String EXTRA_LOCAL_ID = "local_id";

    private LocalDetailAdapter mAdapter;
    private CollapsingToolbarLayout mCollapsing;
    private FloatingActionButton mFab;
    private ImageView mImage;
    private LocalDetailContract.Presenter mPresenter;
    private long mIdCategory;

    public static Fragment newInstance(long id) {
        Fragment fragment = new LocalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_LOCAL_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        setupRecyclerView(view);
        setupViews();

        setHasOptionsMenu(true);

        return view;
    }

    private void setupViews() {
        mCollapsing = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsingToolbarLayout);
        mImage = (ImageView) getActivity().findViewById(R.id.image);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) {
                    mPresenter.saveOrRemoveFavorite();
                }
            }
        });
    }

    @Override
    public void showFavoriteYes() {
        mFab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_white_24dp));
    }

    @Override
    public void showFavoriteNo() {
        mFab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_border_white_24dp));
    }

    @Override
    public void showSnackbarRemoveFavorite() {
        Snackbar.make(mCollapsing, R.string.title_remove_favorite, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbarSaveFavorite() {
        Snackbar.make(mCollapsing, R.string.title_save_favorite, Snackbar.LENGTH_SHORT).show();
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new LocalDetailAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            long id = getArguments().getLong(EXTRA_LOCAL_ID);

            mPresenter = new LocalDetailPresenter(this, id);
            mPresenter.loadLocal(getActivity().getSupportLoaderManager());
        }
    }

    @Override
    public void showLocalDetail(List<LocalDetail> localDetails) {
        mAdapter.replaceData(localDetails);
        getActivity().supportStartPostponedEnterTransition();
    }

    @Override
    public void showTitle(String description) {
        mCollapsing.setTitle(description);
        mCollapsing.setExpandedTitleColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    @Override
    public void showImage(String imagePath) {
        Glide.with(this).load(imagePath).asBitmap()
                .into(new BitmapImageViewTarget(mImage) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        mImage.setBackgroundResource(R.drawable.action_background_bottom);
                        Palette.from(resource).generate(palette -> applyPalette(palette));
                    }
                });
    }

    private void applyPalette(Palette palette) {
        int primary = ContextCompat.getColor(getContext(), R.color.primary);
        mCollapsing.setContentScrimColor(palette.getVibrantColor(primary));
        mCollapsing.setStatusBarScrimColor(palette.getVibrantColor(primary));
    }

    @Override
    public void setIdCategory(long mIdCategory) {
        this.mIdCategory = mIdCategory;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent upIntent = NavUtils.getParentActivityIntent(getActivity());
                upIntent.putExtra(LocalActivity.EXTRA_CATEGORY, mIdCategory);
                upIntent.putExtra(LocalActivity.EXTRA_ID_TITLE, Utility.getIdDescriptionCategory(mIdCategory));

                if (NavUtils.shouldUpRecreateTask(getActivity(), upIntent)) {

                    TaskStackBuilder.create(getActivity())
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();

                } else {
                    NavUtils.navigateUpTo(getActivity(), upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
