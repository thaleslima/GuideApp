package com.guideapp.guideapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.data.remote.GuideApi;
import com.guideapp.guideapp.data.remote.RestClient;
import com.guideapp.guideapp.utilities.Constants;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.wrapper.ListResponse;
import com.guideapp.guideapp.utilities.DataUtil;

import java.util.Set;

public class GuideSyncTask {

    synchronized public static void syncGuide(Context context) {
        try {
            GuideApi service = RestClient.getClient();
            ListResponse<Local> locals = service.getLocals(Constants.City.ID).execute().body();

            ContentResolver contentResolver = context.getContentResolver();

            Cursor cursor = contentResolver.query(GuideContract.LocalEntry.CONTENT_URI, null,
                    GuideContract.LocalEntry.getSqlSelectForFavorites(), null, null);

            Set<Long> favorites = DataUtil.getLocalsFavoritesFromCursor(cursor);
            ContentValues[] values = DataUtil.getGuideContentValuesFromList(locals.getItems(), favorites);

            if (values != null && values.length != 0) {
                contentResolver.bulkInsert(
                        GuideContract.LocalEntry.CONTENT_URI,
                        values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}