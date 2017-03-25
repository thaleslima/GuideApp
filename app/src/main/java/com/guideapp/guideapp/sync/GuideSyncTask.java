package com.guideapp.guideapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.util.Log;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.data.remote.GuideApi;
import com.guideapp.guideapp.data.remote.RestClient;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.wrapper.ListResponse;
import com.guideapp.guideapp.utilities.Constants;
import com.guideapp.guideapp.utilities.DataUtil;
import com.guideapp.guideapp.utilities.Utility;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public class GuideSyncTask {
    public static final int LOCATION_STATUS_OK = 0;
    public static final int LOCATION_STATUS_SERVER_DOWN = 1;
    public static final int LOCATION_STATUS_NO_CONNECTION = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOCATION_STATUS_OK, LOCATION_STATUS_SERVER_DOWN, LOCATION_STATUS_NO_CONNECTION})
    public @interface SyncStatus {
    }

    private static final String TAG = GuideSyncTask.class.getName();

    static synchronized void syncGuide(Context context) {
        try {

            if (!Utility.isNetworkAvailable(context)) {
                setLocationStatus(context, LOCATION_STATUS_NO_CONNECTION);
                notifySync(context);
                return;
            }

            GuideApi service = RestClient.getClient();
            ListResponse<Local> locals = service.getLocals(Constants.City.ID).execute().body();
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(GuideContract.LocalEntry.CONTENT_URI, null,
                    GuideContract.LocalEntry.getSqlSelectForFavorites(), null, null);
            Set<Long> favorites = DataUtil.getLocalsFavoritesFromCursor(cursor);
            ContentValues[] values = DataUtil.getGuideContentValuesFromList(locals.getItems(), favorites);
            if (values != null && values.length != 0) {
                contentResolver.bulkInsert(GuideContract.LocalEntry.CONTENT_URI, values);
            }

            setLocationStatus(context, LOCATION_STATUS_OK);

        } catch (Exception e) {
            setLocationStatus(context, LOCATION_STATUS_SERVER_DOWN);
            notifySync(context);
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private static void notifySync(Context context) {
        Intent dataUpdatedIntent = new Intent(Constants.ACTION_DATA_SYNC_ERROR)
                .setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }

    @SuppressWarnings("ResourceType")
    public static @GuideSyncTask.SyncStatus int getSyncStatus(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getInt(c.getString(R.string.pref_location_status_key), GuideSyncTask.LOCATION_STATUS_OK);
    }

    private static void setLocationStatus(Context c, @GuideSyncTask.SyncStatus int syncStatus) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(c.getString(R.string.pref_location_status_key), syncStatus);
        spe.commit();
    }
}