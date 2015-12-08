package com.guideapp.guideapp.UI.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.adapters.LocalAdapter;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.UI.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;
import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends BaseActivity implements RecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private LocalAdapter mAdapter;
    private List<Local> mDataSet;
    private LinearLayout mLinearLayout;

    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFindViewById();
        setViewProperties();
    }

    private void setFindViewById() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout2);
    }

    private void setViewProperties() {
        mDataSet = new ArrayList<>();
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));

        mAdapter = new LocalAdapter(this, this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 310));
    }

    @Override
    public void onItemClick(View view, int position) {
        LocalDetailActivity.navigate(this, view.findViewById(R.id.local_picture));
    }
}
