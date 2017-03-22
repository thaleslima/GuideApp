package com.guideapp.guideapp.data.local;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class GuideProvider extends ContentProvider {
    private static final String TAG = GuideProvider.class.getName();

    public static final int CODE_LOCAL = 100;
    public static final int CODE_LOCAL_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private GuideDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GuideContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, GuideContract.PATH_LOCAL, CODE_LOCAL);
        matcher.addURI(authority, GuideContract.PATH_LOCAL + "/#", CODE_LOCAL_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new GuideDbHelper(getContext());

        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Log.d(TAG, "bulkInsert: " + uri);

        switch (sUriMatcher.match(uri)) {
            case CODE_LOCAL:
                db.beginTransaction();
                int rowsInserted = 0;

                try {
                    db.delete(GuideContract.LocalEntry.TABLE_NAME, null, null);

                    for (ContentValues value : values) {
                        long _id = db.insert(GuideContract.LocalEntry.TABLE_NAME, null, value);

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }

                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Log.d(TAG, "notifyChange: " + uri);
                }

                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: " + uri);
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case CODE_LOCAL: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        GuideContract.LocalEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CODE_LOCAL_WITH_ID: {
                String id = uri.getPathSegments().get(1);
                cursor = mOpenHelper.getReadableDatabase().query(
                        GuideContract.LocalEntry.TABLE_NAME,
                        projection,
                        GuideContract.LocalEntry._ID + " = ?",
                        new String[]{id},
                        null,
                        null,
                        null);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new RuntimeException("We are not implementing insert.");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_LOCAL:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        GuideContract.LocalEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        switch (sUriMatcher.match(uri)) {

            case CODE_LOCAL_WITH_ID:
                String id = uri.getPathSegments().get(1);

                numRowsDeleted = mOpenHelper.getWritableDatabase().update(
                        GuideContract.LocalEntry.TABLE_NAME,
                        values,
                        GuideContract.LocalEntry._ID + " = ?",
                        new String[]{id});

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numRowsDeleted;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
