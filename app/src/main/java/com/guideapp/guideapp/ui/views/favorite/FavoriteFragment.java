package com.guideapp.guideapp.ui.views.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.localdetail.LocalDetailActivity;
import com.guideapp.guideapp.ui.adapters.FavoriteAdapter;
import com.guideapp.guideapp.ui.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.ui.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class FavoriteFragment extends Fragment implements RecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private List<Local> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_favorite, container, false);
        initRecyclerView(layoutView);
        return layoutView;
    }

    private void initRecyclerView(View layoutView) {
        mRecyclerView = (RecyclerView) layoutView.findViewById(R.id.local_list);

        mDataSet = new ArrayList<>();
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));

        mAdapter = new FavoriteAdapter(this.getActivity(), this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL_LIST, 310));
    }

    @Override
    public void onItemClick(View view, int position) {
        //LocalDetailActivity.navigate(this.getActivity(), view.findViewById(R.id.local_picture));
        LocalDetailActivity.navigate(this.getActivity());

    }
}
