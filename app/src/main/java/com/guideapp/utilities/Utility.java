package com.guideapp.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.guideapp.R;
import com.guideapp.model.Local;
import com.guideapp.model.MainMenu;

import java.util.ArrayList;
import java.util.List;

public final class Utility {
    private Utility() {
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void updateWidgets(Context context) {
        Intent dataUpdatedIntent = new Intent(Constants.ACTION_DATA_UPDATED)
                .setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }

    public static String getTextToShare(Context context, Local local) {
        return context.getString(R.string.share_text,
                local.getDescription(),
                local.getAddress(),
                local.getDescriptionSubCategories(),
                "http://maps.google.com/maps?saddr=" + local.getLatitude() + "," + local.getLongitude());
    }


    public static int getIdDescriptionCategory(long id) {
        if (id == Constants.Menu.ACCOMMODATION) {
            return R.string.menu_accommodation;
        }

        if (id == Constants.Menu.ALIMENTATION) {
            return R.string.menu_alimentation;
        }

        if (id == Constants.Menu.ATTRACTIVE) {
            return R.string.menu_attractive;
        }

        return 0;
    }

    public static int getIdImageCategory(long id) {
        if (id == Constants.Menu.ACCOMMODATION) {
            return R.drawable.ic_place_hotel_48dp;
        }

        if (id == Constants.Menu.ALIMENTATION) {
            return R.drawable.ic_place_dining_48dp;
        }

        if (id == Constants.Menu.ATTRACTIVE) {
            return R.drawable.ic_place_terrain_48dp;
        }

        return 0;
    }

    public static List<MainMenu> getMenus() {
        List<MainMenu> menus = new ArrayList<>();

        menus.add(new MainMenu(1, R.string.menu_local, R.drawable.ic_map_white_36dp, R.color.green_500));
        menus.add(new MainMenu(Constants.Menu.ALIMENTATION, R.string.menu_alimentation,
                R.drawable.ic_local_dining_white_36dp, R.color.blue_500));
        menus.add(new MainMenu(Constants.Menu.ATTRACTIVE, R.string.menu_attractive,
                R.drawable.ic_terrain_white_36dp, R.color.cyan_500));
        menus.add(new MainMenu(Constants.Menu.ACCOMMODATION, R.string.menu_accommodation,
                R.drawable.ic_local_hotel_white_36dp, R.color.purple_500));

        return menus;
    }
}
