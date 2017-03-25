package com.guideapp.guideapp.ui.views.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenu;
import com.guideapp.guideapp.ui.views.GridSpacingItemDecoration;
import com.guideapp.guideapp.ui.views.local.LocalActivity;
import com.guideapp.guideapp.ui.views.map.MapActivity;
import com.guideapp.guideapp.utilities.Constants;
import com.guideapp.guideapp.utilities.Utility;

public class MenuFragment extends Fragment implements MenuAdapter.RecyclerViewItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        MenuAdapter adapter = new MenuAdapter(Utility.getMenus(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));
    }

    @Override
    public void onItemClick(MainMenu item) {
        if (item.getId() == 1) {
            MapActivity.navigate(this.getActivity(), Constants.City.ID);
            return;
        }

        LocalActivity.navigate(this.getActivity(), item.getId(), item.getIdTitle());
    }
}
