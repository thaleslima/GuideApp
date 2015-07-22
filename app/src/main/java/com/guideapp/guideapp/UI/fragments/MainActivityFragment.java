package com.guideapp.guideapp.UI.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.activities.LocalActivity;
import com.guideapp.guideapp.UI.adapters.RecyclerViewAdapter;
import com.guideapp.guideapp.UI.widget.GridSpacingItemDecoration;
import com.guideapp.guideapp.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener {
    private static List<ViewModel> items = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        initMenu();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getActivity(), items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 16, false));
    }

    private void initMenu()
    {
        items.add(new ViewModel(1, R.string.menu_alimentation, R.drawable.ic_local_dining_white_24dp, R.color.blue_500));
        items.add(new ViewModel(1, R.string.menu_alimentation, R.drawable.ic_local_dining_white_24dp, R.color.green_500));
        items.add(new ViewModel(1, R.string.menu_alimentation, R.drawable.ic_local_dining_white_24dp, R.color.red_500));
        items.add(new ViewModel(1, R.string.menu_alimentation, R.drawable.ic_local_dining_white_24dp, R.color.purple_500));
        items.add(new ViewModel(1, R.string.menu_alimentation, R.drawable.ic_local_dining_white_24dp, R.color.pink_500));
        items.add(new ViewModel(1, R.string.menu_alimentation, R.drawable.ic_local_dining_white_24dp, R.color.cyan_500));
    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        LocalActivity.navigate(this.getActivity());
    }
}
