package com.guideapp.guideapp.ui.views.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.infrastructure.Constants;
import com.guideapp.guideapp.ui.views.local.LocalActivity;
import com.guideapp.guideapp.ui.views.map.MapActivity;
import com.guideapp.guideapp.ui.adapters.RecyclerViewAdapter;
import com.guideapp.guideapp.ui.widget.GridSpacingItemDecoration;
import com.guideapp.guideapp.model.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment
        implements RecyclerViewAdapter.OnItemClickListener {
    private static List<MainMenu> items;

    public MainFragment() {
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

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));
    }

    private void initMenu() {
        if (items == null) {
            items = new ArrayList<>();
            items.add(new MainMenu(
                    1,
                    R.string.menu_local,
                    R.drawable.ic_map_white_36dp,
                    R.color.green_500));
            items.add(new MainMenu(
                    Constants.Menu.ALIMENTATION,
                    R.string.menu_alimentation,
                    R.drawable.ic_local_dining_white_36dp,
                    R.color.blue_500,
                    R.color.blue_700,
                    R.style.AppThemeBlue));
            items.add(new MainMenu(
                    Constants.Menu.ATTRACTIVE,
                    R.string.menu_attractive,
                    R.drawable.ic_terrain_white_36dp,
                    R.color.cyan_500,
                    R.color.cyan_700,
                    R.style.AppThemeCyan));
            items.add(new MainMenu(
                    Constants.Menu.ACCOMMODATION,
                    R.string.menu_accommodation,
                    R.drawable.ic_local_hotel_white_36dp,
                    R.color.purple_500,
                    R.color.purple_700,
                    R.style.AppThemePurple));
        }
    }

    @Override
    public void onItemClick(View view, MainMenu mainMenu) {
        if (mainMenu.getId() == 1) {
            MapActivity.navigate(this.getActivity());
        } else {
            LocalActivity.navigate(this.getActivity(),
                    Constants.City.ID,
                    mainMenu.getId(),
                    mainMenu.getIdText(),
                    mainMenu.getTheme(),
                    mainMenu.getColorDark());
        }
    }
}
