package com.guideapp.guideapp.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableList;

public class GuideContract {
    static final String CONTENT_AUTHORITY = "com.guideapp.guideapp";
    static final String PATH_LOCAL = "local";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class LocalEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LOCAL)
                .build();

        public static final String TABLE_NAME = "local";

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_WIFI = "wifi";
        public static final String COLUMN_DETAIL = "detail";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_IMAGE_PATH = "imagePath";
        public static final String COLUMN_ID_CITY = "idCity";
        public static final String COLUMN_ID_CATEGORY = "idCategory";
        public static final String COLUMN_ID_SUB_CATEGORY = "idSubCategories";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_DESCRIPTION_SUB_CATEGORY = "descriptionSubCategories";
        public static final String COLUMN_FAVORITE = "isFavorite";

        public static final int POSITION_ID = 0;
        public static final int POSITION_DESCRIPTION = 1;
        public static final int POSITION_SITE = 2;
        public static final int POSITION_PHONE = 3;
        public static final int POSITION_ADDRESS = 4;
        public static final int POSITION_WIFI = 5;
        public static final int POSITION_DETAIL = 6;
        public static final int POSITION_LATITUDE = 7;
        public static final int POSITION_LONGITUDE = 8;
        public static final int POSITION_IMAGE_PATH = 9;
        public static final int POSITION_ID_CITY = 10;
        public static final int POSITION_ID_CATEGORY = 11;
        public static final int POSITION_ID_SUB_CATEGORY = 12;
        public static final int POSITION_TIMESTAMP = 13;
        public static final int POSITION_DESCRIPTION_SUB_CATEGORY = 14;
        public static final int POSITION_FAVORITE = 15;

        public static final ImmutableList<String> COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_DESCRIPTION,
                COLUMN_SITE,
                COLUMN_PHONE,
                COLUMN_ADDRESS,
                COLUMN_WIFI,
                COLUMN_DETAIL,
                COLUMN_LATITUDE,
                COLUMN_LONGITUDE,
                COLUMN_IMAGE_PATH,
                COLUMN_ID_CITY,
                COLUMN_ID_CATEGORY,
                COLUMN_ID_SUB_CATEGORY,
                COLUMN_TIMESTAMP,
                COLUMN_DESCRIPTION_SUB_CATEGORY,
                COLUMN_FAVORITE
        );


        public static Uri buildLocalUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

        public static String getSqlSelectForIdCategory(long idCategory) {
            return GuideContract.LocalEntry.COLUMN_ID_CATEGORY + " = " + idCategory;
        }

        public static String getSqlSelectForFavorites() {
            return GuideContract.LocalEntry.COLUMN_FAVORITE + " = " + 1;
        }
    }
}
