package com.guideapp.guideapp.ui.views.whatshot;

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
import com.guideapp.guideapp.ui.adapters.WhatsHotAdapter;
import com.guideapp.guideapp.ui.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.ui.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Opinion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class WhatsHotFragment extends Fragment implements RecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private WhatsHotAdapter mAdapter;
    private List<Opinion> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_whatshot, container, false);
        initRecyclerView(layoutView);
        return layoutView;
    }

    private void initRecyclerView(View layoutView) {
        mRecyclerView = (RecyclerView) layoutView.findViewById(R.id.recycler_view);

        mDataSet = new ArrayList<>();
        mDataSet.add(new Opinion(1, "thales Lima", null, new Date(), "Pousada da Lua", 4));
        mDataSet.add(new Opinion(2, "thales Lima", null, new Date(), "Pousada da Lua", 3));
        mDataSet.add(new Opinion(3, "thales Lima", null, new Date(), "Pousada da Lua", 5));
        mDataSet.add(new Opinion(4, "thales Lima", null, new Date(), "Pousada da Lua", 2));
        mDataSet.add(new Opinion(5, "thales Lima", null, new Date(), "Pousada da Lua", 3));
        mDataSet.add(new Opinion(6, "thales Lima", null, new Date(), "Pousada da Lua", 5));

        mAdapter = new WhatsHotAdapter(this.getActivity(), mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onItemClick(View view, int position) {
        //LocalDetailActivity.navigate(this.getActivity(), view.findViewById(R.id.local_picture));
        LocalDetailActivity.navigate(this.getActivity());
    }
}
