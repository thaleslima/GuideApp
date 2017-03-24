package com.guideapp.guideapp.utilities;

import android.content.Context;
import android.content.Intent;

import com.guideapp.guideapp.R;

public final class Utility {
    private Utility() {
    }

    public static void updateWidgets(Context context) {
        Intent dataUpdatedIntent = new Intent(Constants.ACTION_DATA_UPDATED)
                .setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
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
}
