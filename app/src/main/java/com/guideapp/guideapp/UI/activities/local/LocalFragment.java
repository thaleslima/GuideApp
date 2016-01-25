package com.guideapp.guideapp.UI.activities.local;

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

        mAdapter = new LocalAdapter(this.getActivity(), this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL_LIST, 310));
    }

    @Override
    public void onItemClick(View view, int position) {
        LocalDetailActivity.navigate(this.getActivity(), view.findViewById(R.id.local_picture));
    }
}
