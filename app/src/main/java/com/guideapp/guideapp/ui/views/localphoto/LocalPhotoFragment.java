package com.guideapp.guideapp.ui.views.localphoto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.photoview.PhotoViewerActivity;
import com.guideapp.guideapp.ui.adapters.PhotoAdapter;
import com.guideapp.guideapp.ui.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.ui.widget.GridSpacingItemDecoration;
import com.guideapp.guideapp.model.MainMenuTemp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalPhotoFragment extends Fragment implements RecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;
    private List<MainMenuTemp> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_local_photo, container, false);
        initRecyclerView(layoutView);
        return layoutView;
    }

    private void initRecyclerView(View layoutView) {
        mRecyclerView = (RecyclerView) layoutView.findViewById(R.id.recycler_view);
        mDataSet = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            mDataSet.add(new MainMenuTemp(i, "http://lorempixel.com/500/500/animals/" + i));
        }

        mAdapter = new PhotoAdapter(this.getActivity(), mDataSet);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));
    }

    @Override
    public void onItemClick(View view, int position) {
        PhotoViewerActivity.navigate(this.getActivity(), position);
    }
}
