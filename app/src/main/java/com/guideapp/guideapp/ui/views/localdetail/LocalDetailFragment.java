package com.guideapp.guideapp.ui.views.localdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.LocalDetail;
import com.guideapp.guideapp.ui.adapters.LocalDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalDetailFragment extends Fragment implements LocalDetailContract.View {
    private LocalDetailAdapter mAdapter;
    private List<LocalDetail> mDataSet;
    private LocalDetailContract.UserActionsListener mActionsListener;
    private ProgressBar mProgressBar;
    private Local mLocal;

    private static final String EXTRA_LOCAL = "local";

    /**
     *  Create new instance
     * @param local Local object
     * @return Return LocalDetailFragment instance
     */
    public static Fragment newInstance(Local local) {
        Fragment fragment = new LocalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_LOCAL, local);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_local, container, false);

        initExtra();
        setFindViewById(layoutView);
        initRecyclerView(layoutView);

        mActionsListener = new LocalDetailPresenter(this);

        return layoutView;
    }

    /**
     * Initialize extras parameters
     */
    private void initExtra() {
        if (getArguments() != null) {
            mLocal = getArguments().getParcelable(EXTRA_LOCAL);
        }
    }

    /**
     * Initialize views by id
     */
    private void setFindViewById(View layoutView) {
        mProgressBar = (ProgressBar) layoutView.findViewById(R.id.progressBar);
    }

    /**
     * Initialize recycler view
     */
    private void initRecyclerView(View layoutView) {
        RecyclerView recyclerView = (RecyclerView) layoutView.findViewById(R.id.recycler);

        mDataSet = new ArrayList<>();

        mAdapter = new LocalDetailAdapter(this.getActivity(), mDataSet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActionsListener.loadLocal(mLocal);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActionsListener.unsubscribe();
    }


    @Override
    public void showLocalDetail(List<LocalDetail> localDetails) {
        mAdapter.replaceData(localDetails);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
