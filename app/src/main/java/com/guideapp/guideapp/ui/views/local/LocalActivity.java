package com.guideapp.guideapp.ui.views.local;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.ui.views.BaseActivity;
import com.guideapp.guideapp.ui.views.localdetail.LocalDetailActivity;
import com.guideapp.guideapp.ui.views.DividerItemDecoration;

import java.util.List;

public class LocalActivity extends BaseActivity implements LocalContract.View, LocalAdapter.ItemClickListener {
    private static final String EXTRA_CATEGORY = "id-category";
    private static final String EXTRA_ID_TITLE = "id-title";

    private ProgressBar mProgressBar;

    private long mIdCategory;
    private int mIdTitle;

    private LocalAdapter mAdapter;
    private LocalContract.Presenter mActionsListener;

    public static void navigate(Context context, Long idCategory, int idTitle) {
        Intent intent = new Intent(context, LocalActivity.class);
        intent.putExtra(EXTRA_CATEGORY, idCategory);
        intent.putExtra(EXTRA_ID_TITLE, idTitle);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        initExtra();
        setupViews();
        setupToolbar();
        setupRecyclerView();

        mActionsListener = new LocalPresenter(this, mIdCategory);
        mActionsListener.loadLocals(getSupportLoaderManager());
    }

    private void initExtra() {
        mIdCategory = getIntent().getLongExtra(EXTRA_CATEGORY, 0);
        mIdTitle = getIntent().getIntExtra(EXTRA_ID_TITLE, 0);
    }

    private void setupViews() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mIdTitle);
        }
    }

    private void setupRecyclerView() {
        mAdapter = new LocalAdapter(this, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void showLocals(List<Local> locals) {
        mAdapter.replaceData(locals);
    }

    @Override
    public void showLocalDetailUi(Local local, ImageView view) {
        LocalDetailActivity.navigate(this, view, local.getId());
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(Local item, ImageView view) {
        mActionsListener.openLocalDetails(item, view);
    }
}
