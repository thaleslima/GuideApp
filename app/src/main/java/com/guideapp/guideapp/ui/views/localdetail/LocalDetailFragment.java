package com.guideapp.guideapp.ui.views.localdetail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.LocalDetail;

import java.util.List;

public class LocalDetailFragment extends Fragment implements LocalDetailContract.View {
    private static final String EXTRA_LOCAL_ID = "local_id";

    private LocalDetailAdapter mAdapter;
    private CollapsingToolbarLayout mCollapsing;
    private FloatingActionButton mFab;
    private ImageView mImage;
    private LocalDetailContract.Presenter mPresenter;

    public static Fragment newInstance(long id) {
        Fragment fragment = new LocalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_LOCAL_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        setupRecyclerView(view);
        setupViews();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                mPresenter.shareLocal();
                break;
        }

        return true;
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
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) {
            mPresenter.destroy(getActivity().getSupportLoaderManager());
            mPresenter = null;
        }
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
    public void shareText(String textToShare) {
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this.getActivity())
                .setType(mimeType)
                .setChooserTitle(R.string.app_name)
                .setText(textToShare)
                .startChooser();
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
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }
                });
    }

    private void applyPalette(Palette palette) {
        int primary = ContextCompat.getColor(getContext(), R.color.primary);
        mCollapsing.setContentScrimColor(palette.getVibrantColor(primary));
        mCollapsing.setStatusBarScrimColor(palette.getVibrantColor(primary));
    }
}
