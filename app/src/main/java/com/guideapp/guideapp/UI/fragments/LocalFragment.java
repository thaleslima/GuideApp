package com.guideapp.guideapp.UI.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.activities.LocalDetailActivity;
import com.guideapp.guideapp.UI.adapters.LocalAdapter;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.UI.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalFragment extends Fragment implements RecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private LocalAdapter mAdapter;
    private List<Local> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_local, container, false);

        setFindViewById(layoutView);
        setViewProperties();

        return layoutView;
    }

    private void setViewProperties() {
        mDataSet = new ArrayList<Local>();
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));

        mAdapter = new LocalAdapter(this.getActivity(), this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void setFindViewById(View layoutView) {
        mRecyclerView = (RecyclerView) layoutView.findViewById(R.id.local_list);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent it = new Intent(this.getActivity(), LocalDetailActivity.class);
        startActivity(it);
    }
}
