package com.guideapp.guideapp.ui.views.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.localdetail.LocalDetailActivity;
import com.guideapp.guideapp.ui.adapters.LocalAdapter;
import com.guideapp.guideapp.ui.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalFragment extends Fragment
        implements LocalAdapter.RecyclerViewItemClickListener, LocalContract.ViewFragment {
    private LocalAdapter mAdapter;
    private LocalContract.UserActionsFragmentListener mActionsListener;
    private ProgressBar mProgressBar;

    private static final String EXTRA_CITY = "id-city";
    private static final String EXTRA_CATEGORY = "id-category";
    private static final String EXTRA_SUB_CATEGORY = "id-sub-category";

    private long mIdCity;
    private long mIdCategory;
    private long[] mIdSubCategories;

    /**
     * Create new instance
     * @param idCity Id city
     * @param idCategory Id Category
     * @param idSubCategories Id Sub category
     * @return Return LocalFragment instance
     */
    public static Fragment newInstance(long idCity, long idCategory, long[] idSubCategories) {
        Fragment fragment = new LocalFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CITY, idCity);
        bundle.putLong(EXTRA_CATEGORY, idCategory);
        bundle.putLongArray(EXTRA_SUB_CATEGORY, idSubCategories);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_local, container, false);

        setFindViewById(layoutView);
        initRecyclerView(layoutView);
        initExtra();

        mActionsListener = new LocalFragmentPresenter(this, getContext());

        return layoutView;
    }

    /**
     * Initialize extras parameters
     */
    private void initExtra() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            mIdCity = bundle.getLong(EXTRA_CITY, 0);
            mIdCategory = bundle.getLong(EXTRA_CATEGORY, 0);
            mIdSubCategories = bundle.getLongArray(EXTRA_SUB_CATEGORY);
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

        mAdapter = new LocalAdapter(this.getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL_LIST, 310));
    }

    @Override
    public void onItemClick(Local item, ImageView view) {
        mActionsListener.openLocalDetails(item, view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActionsListener.loadLocals(mIdCity, mIdCategory, mIdSubCategories);
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
    public void showLocals(List<Local> locals) {
        mAdapter.replaceData(locals);
    }

    @Override
    public void showLocalDetailUi(Local local, ImageView view) {
        LocalDetailActivity.navigate(this.getActivity(), local);
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
