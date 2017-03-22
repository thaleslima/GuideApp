package com.guideapp.guideapp.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

public class GuideContract {
    public static final String CONTENT_AUTHORITY = "com.guideapp.guideapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOCAL = "local";
    public static final String PATH_LOCAL_FAVORITES = "localFavorites";

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
