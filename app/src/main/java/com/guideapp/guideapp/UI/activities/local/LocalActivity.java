package com.guideapp.guideapp.UI.activities.local;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.activities.BaseActivity;
import com.guideapp.guideapp.UI.activities.LocalDetailActivity;
import com.guideapp.guideapp.UI.adapters.LocalAdapter;
import com.guideapp.guideapp.UI.fragments.FilterDialogFragment;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.UI.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalActivity extends BaseActivity implements RecyclerViewItemClickListener, LocalContract.View {
    private LocalAdapter mAdapter;
    private LocalContract.UserActionsListener mActionsListener;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler) RecyclerView mRecyclerView;
    @Bind(R.id.button_filter) ImageView mButtonFilter;

    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        ButterKnife.bind(this);

        mActionsListener = new LocalPresenter();

        initToolbar();
        initRecyclerView();
        setViewProperties();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        mAdapter = new LocalAdapter(this, this, new ArrayList<Local>(0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 310));
    }

    private void setViewProperties() {
        mButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new FilterDialogFragment();
                newFragment.show(getFragmentManager(), "missiles");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionsListener.loadLocals(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        mActionsListener.openNoteDetails(new Local("", ""));
    }

    @Override
    public void showLocals(List<Local> locals) {
        mAdapter.replaceData(locals);
    }

    @Override
    public void showLocalDetailUi(Local local) {
        // LocalDetailActivity.navigate(this, view.findViewById(R.id.local_picture));
        LocalDetailActivity.navigate(this);
    }
}
