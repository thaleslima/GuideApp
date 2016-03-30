package com.guideapp.guideapp.ui.views.search;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenuTemp;
import com.guideapp.guideapp.ui.adapters.LocalAdapter;
import com.guideapp.guideapp.ui.adapters.SearchAdapter;
import com.guideapp.guideapp.ui.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.ui.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class SearchFragment extends Fragment implements SearchAdapter.OnItemClickListener,
        RecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private List<MainMenuTemp> mDataSet;
    private String mQuery = "";
    private List<Local> mDataSetResult;
    private LocalAdapter mAdapterResult;
    private DividerItemDecoration mDividerItemDecoration;

    private boolean mResult;

    private Callbacks mCallbacks = sCallbacks;
    private ProgressBar mProgressBar;

    private static Callbacks sCallbacks = new Callbacks() {

        @Override
        public void setQuery(String query) {

        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sCallbacks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_search, container, false);
        setFindViewById(layoutView);
        initRecyclerView(layoutView);
        return layoutView;
    }

    private void setFindViewById(View layoutView) {
        mProgressBar = (ProgressBar) layoutView.findViewById(R.id.progress_View);
    }

    private void initRecyclerView(View layoutView) {
        mRecyclerView = (RecyclerView) layoutView.findViewById(R.id.recycler_View);
        mDataSet = new ArrayList<>();
        mDividerItemDecoration = new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL_LIST, 310);
        showSearchRecent();
    }

    private void initSearchDataSet() {
        mDataSet.clear();
        mDataSet.add(new MainMenuTemp(1, "Complexo do claro " + mQuery));
        mDataSet.add(new MainMenuTemp(2, "gruta " + mQuery));
        mDataSet.add(new MainMenuTemp(3, "pousada " + mQuery));
        mDataSet.add(new MainMenuTemp(4, "restaurante " + mQuery));

        mAdapter = new SearchAdapter(this.getActivity(), mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.removeItemDecoration(mDividerItemDecoration);
        mAdapter.setOnItemClickListener(this);
    }

    private void initResultDataSet() {
        mDataSetResult = new ArrayList<>();
        mDataSetResult.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSetResult.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSetResult.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSetResult.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSetResult.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSetResult.add(new Local("Cachoeira da gruta", "Complexo do claro"));

        mAdapterResult = new LocalAdapter(this.getActivity(), null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapterResult);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        hideProgressBar();
    }

    public SearchFragment setQuery(final String query) {
        this.mQuery = query;
        return this;
    }

    @Override
    public void onItemClick(View view, MainMenuTemp mainMenu) {
        showResult(mainMenu.getText());
    }

    public void showSearchRecent() {
        showSearchRecent("");
    }

    public void showSearchRecent(String query) {
        setQuery(query);
        setShowResult(false);
        initSearchDataSet();
    }

    public void showResult(String query) {
        if (query != null && !query.isEmpty()) {
            setQuery(query);
            mCallbacks.setQuery(query);
            setShowResult(true);
            showProgressBar();
            cleanRecyclerView();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initResultDataSet();
                }
            }, 400);
        }
    }

    private void cleanRecyclerView() {
        if (mDataSetResult != null)
            mDataSetResult.clear();
        if (mDataSet != null)
            mDataSet.clear();

        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void setQuery(String query);
    }

    private SearchFragment setShowResult(boolean showResult) {
        mResult = showResult;
        return this;
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
