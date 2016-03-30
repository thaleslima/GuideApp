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
import com.guideapp.guideapp.model.MainMenu;
import com.guideapp.guideapp.ui.views.local.LocalActivity;
import com.guideapp.guideapp.ui.views.map.MapActivity;
import com.guideapp.guideapp.ui.adapters.MenuAdapter;
import com.guideapp.guideapp.ui.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Fragment
 */
public class MainFragment extends Fragment
        implements MenuAdapter.RecyclerViewItemClickListener {
    private static List<MainMenu> mItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initRecyclerView(view);

        return view;
    }

    /**
     * Initialize recycler view
     */
    private void initRecyclerView(View view) {
        initItemsRecyclerView();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        MenuAdapter adapter = new MenuAdapter(mItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));
    }

    /**
     * Initialize recycler view's items
     */
    private void initItemsRecyclerView() {
        if (mItems == null) {
            mItems = new ArrayList<>();
            mItems.add(new MainMenu(
                    1,
                    R.string.menu_local,
                    R.drawable.ic_map_white_36dp,
                    R.color.green_500));
            mItems.add(new MainMenu(
                    Constants.Menu.ALIMENTATION,
                    R.string.menu_alimentation,
                    R.drawable.ic_local_dining_white_36dp,
                    R.color.blue_500,
                    R.color.blue_700,
                    R.style.AppThemeBlue));
            mItems.add(new MainMenu(
                    Constants.Menu.ATTRACTIVE,
                    R.string.menu_attractive,
                    R.drawable.ic_terrain_white_36dp,
                    R.color.cyan_500,
                    R.color.cyan_700,
                    R.style.AppThemeCyan));
            mItems.add(new MainMenu(
                    Constants.Menu.ACCOMMODATION,
                    R.string.menu_accommodation,
                    R.drawable.ic_local_hotel_white_36dp,
                    R.color.purple_500,
                    R.color.purple_700,
                    R.style.AppThemePurple));
        }
    }

    @Override
    public void onItemClick(MainMenu item) {
        if (item.getId() == 1) {
            MapActivity.navigate(
                    this.getActivity(),
                    Constants.City.ID);
            return;
        }

        LocalActivity.navigate(this.getActivity(),
                Constants.City.ID,
                item.getId(),
                item.getIdTitle(),
                item.getIdTheme(),
                item.getIdColorPrimaryDark());
    }
}
