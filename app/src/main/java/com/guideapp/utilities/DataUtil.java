package com.guideapp.utilities;

import android.content.ContentValues;
import android.database.Cursor;

import com.guideapp.data.local.GuideContract;
import com.guideapp.model.Local;
import com.guideapp.model.SubCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DataUtil {
    private DataUtil() {

    }

    public static Set<Long> getLocalsFavoritesFromCursor(Cursor cursor) {
        Set<Long> longSet = new HashSet<>();

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(GuideContract.LocalEntry._ID);

            do {
                longSet.add(cursor.getLong(columnIndex));
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return longSet;
    }

    public static List<Local> getLocalsFromCursor(Cursor cursor) {
        List<Local> locals = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(GuideContract.LocalEntry._ID);
            int columnDescription = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DESCRIPTION);
            int columnSite = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_SITE);
            int columnPhone = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_PHONE);
            int columnAddress = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ADDRESS);
            int columnWifi = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_WIFI);
            int columnDetail = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DETAIL);
            int columnLatitude = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_LATITUDE);
            int columnLongitude = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_LONGITUDE);
            int columnImagePath = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_IMAGE_PATH);
            int columnIdCategory = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_CATEGORY);
            int columnIndexIdCity = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_CITY);
            int columnIndexTimestamp = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_TIMESTAMP);
            int columnIndexDescriptionSubCategory
                    = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY);
            int columnIndexFavorite = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_FAVORITE);

            do {
                Local local = new Local();
                local.setId(cursor.getLong(columnIndex));
                local.setDescription(cursor.getString(columnDescription));
                local.setSite(cursor.getString(columnSite));
                local.setPhone(cursor.getString(columnPhone));
                local.setAddress(cursor.getString(columnAddress));
                local.setWifi(cursor.getInt(columnWifi) == 1);
                local.setDetail(cursor.getString(columnDetail));
                local.setLatitude(cursor.getDouble(columnLatitude));
                local.setLongitude(cursor.getDouble(columnLongitude));
                local.setImagePath(cursor.getString(columnImagePath));
                local.setIdCategory(cursor.getLong(columnIdCategory));
                local.setIdCity(cursor.getLong(columnIndexIdCity));
                local.setTimestamp(cursor.getLong(columnIndexTimestamp));
                local.setDescriptionSubCategories(cursor.getString(columnIndexDescriptionSubCategory));
                local.setFavorite(cursor.getInt(columnIndexFavorite) == 1);

                locals.add(local);
            }
            while (cursor.moveToNext());
        }

        return locals;
    }


    public static ContentValues[] getGuideContentValuesFromList(List<Local> locals, Set<Long> favorites) {
        List<ContentValues> contentValues = new ArrayList<>();

        for (Local local : locals) {
            ContentValues testValues = new ContentValues();
            testValues.put(GuideContract.LocalEntry._ID, local.getId());
            testValues.put(GuideContract.LocalEntry.COLUMN_DESCRIPTION, local.getDescription());
            testValues.put(GuideContract.LocalEntry.COLUMN_SITE, local.getSite());
            testValues.put(GuideContract.LocalEntry.COLUMN_PHONE, local.getPhone());
            testValues.put(GuideContract.LocalEntry.COLUMN_ADDRESS, local.getAddress());
            testValues.put(GuideContract.LocalEntry.COLUMN_WIFI, local.isWifi());
            testValues.put(GuideContract.LocalEntry.COLUMN_DETAIL, local.getDetail());
            testValues.put(GuideContract.LocalEntry.COLUMN_LATITUDE, local.getLatitude());
            testValues.put(GuideContract.LocalEntry.COLUMN_LONGITUDE, local.getLongitude());
            testValues.put(GuideContract.LocalEntry.COLUMN_IMAGE_PATH, local.getImagePath());
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_CITY, local.getIdCity());
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_CATEGORY, local.getIdCategories().get(0));
            testValues.put(GuideContract.LocalEntry.COLUMN_TIMESTAMP, local.getTimestamp());
            testValues.put(GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY,
                    getDescriptionFromSubCategories(local.getSubCategories()));
            testValues.put(GuideContract.LocalEntry.COLUMN_FAVORITE, favorites.contains(local.getId()));

            contentValues.add(testValues);
        }

        ContentValues[] contentValuesArr = new ContentValues[contentValues.size()];
        contentValuesArr = contentValues.toArray(contentValuesArr);

        return contentValuesArr;
    }

    private static String getDescriptionFromSubCategories(List<SubCategory> subCategories) {
        if (subCategories != null) {
            int size = subCategories.size();
            String description = "";

            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    description += " | ";
                }
                description += subCategories.get(i).getDescription();
            }

            return description;
        }

        return "";
    }
}
