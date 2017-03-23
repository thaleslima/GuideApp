package com.guideapp.guideapp.utilities;

import android.content.Context;
import android.content.Intent;

public final class Utility {
    private Utility() {
    }

    public static void updateWidgets(Context context) {
        Intent dataUpdatedIntent = new Intent(Constants.ACTION_DATA_UPDATED)
                .setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }
}
