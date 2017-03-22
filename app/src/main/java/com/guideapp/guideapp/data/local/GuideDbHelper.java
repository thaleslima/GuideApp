package com.guideapp.guideapp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GuideDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "guide.db";
    private static final int DATABASE_VERSION = 1;

    public GuideDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCAL_TABLE =
                "CREATE TABLE " + GuideContract.LocalEntry.TABLE_NAME + " (" +

                        GuideContract.LocalEntry._ID + " INTEGER PRIMARY KEY, " +
                        GuideContract.LocalEntry.COLUMN_DESCRIPTION + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_SITE + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_PHONE + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_ADDRESS + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_WIFI + " INTEGER, " +
                        GuideContract.LocalEntry.COLUMN_DETAIL + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_LATITUDE + " REAL, " +
                        GuideContract.LocalEntry.COLUMN_LONGITUDE + " REAL, " +
                        GuideContract.LocalEntry.COLUMN_IMAGE_PATH + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_ID_CITY + " INTEGER, " +
                        GuideContract.LocalEntry.COLUMN_ID_CATEGORY + " INTEGER, " +
                        GuideContract.LocalEntry.COLUMN_ID_SUB_CATEGORY + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_TIMESTAMP + " INTEGER, " +
                        GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY + " TEXT, " +
                        GuideContract.LocalEntry.COLUMN_FAVORITE + " INTEGER " +

                        " )";

        db.execSQL(SQL_CREATE_LOCAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GuideContract.LocalEntry.TABLE_NAME);
        onCreate(db);
    }
}
