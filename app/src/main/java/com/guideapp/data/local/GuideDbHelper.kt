package com.guideapp.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GuideDbHelper(context: Context) : SQLiteOpenHelper(context, GuideDbHelper.DATABASE_NAME, null, GuideDbHelper.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateLocalTable = "CREATE TABLE " + GuideContract.LocalEntry.TABLE_NAME + " (" +
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
                GuideContract.LocalEntry.COLUMN_FAVORITE + " INTEGER "+" )"

        db.execSQL(sqlCreateLocalTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + GuideContract.LocalEntry.TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private val DATABASE_NAME = "guide.db"
        private val DATABASE_VERSION = 1
    }
}
