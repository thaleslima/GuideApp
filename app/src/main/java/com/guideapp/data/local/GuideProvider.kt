package com.guideapp.data.local

import android.annotation.TargetApi
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class GuideProvider : ContentProvider() {
    private var mOpenHelper: GuideDbHelper? = null

    override fun onCreate(): Boolean {
        mOpenHelper = GuideDbHelper(context)
        return true
    }

    override fun bulkInsert(uri: Uri, values: Array<ContentValues>): Int {
        var rowsInserted = 0

        mOpenHelper?.writableDatabase?.let { db ->
            Log.d(TAG, "bulkInsert: " + uri)
            when (URI_MATCHER.match(uri)) {
                CODE_LOCAL -> {
                    db.beginTransaction()

                    try {
                        db.delete(GuideContract.LocalEntry.TABLE_NAME, null, null)
                        values.map { db.insert(GuideContract.LocalEntry.TABLE_NAME, null, it) }.filter { it != -1L }.forEach { rowsInserted++ }
                        db.setTransactionSuccessful()
                    } finally {
                        db.endTransaction()
                    }

                    if (rowsInserted > 0) {
                        context?.contentResolver?.notifyChange(uri, null)
                        Log.d(TAG, "notifyChange: " + uri)
                    }
                    return rowsInserted
                }
                else -> return super.bulkInsert(uri, values)
            }
        }

        return rowsInserted
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        Log.d(TAG, "query: " + uri)
        val cursor: Cursor?

        when (URI_MATCHER.match(uri)) {
            CODE_LOCAL -> {
                cursor = mOpenHelper?.readableDatabase?.query(
                        GuideContract.LocalEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder)
            }

            CODE_LOCAL_WITH_ID -> {
                val id = uri.pathSegments[1]
                cursor = mOpenHelper?.readableDatabase?.query(
                        GuideContract.LocalEntry.TABLE_NAME,
                        projection,
                        GuideContract.LocalEntry._ID + " = ?",
                        arrayOf(id), null, null, null)
            }

            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        throw RuntimeException("We are not implementing getType.")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw RuntimeException("We are not implementing insert.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var selection1 = selection
        val numRowsDeleted: Int?
        if (null == selection1) selection1 = "1"

        when (URI_MATCHER.match(uri)) {
            CODE_LOCAL -> numRowsDeleted = mOpenHelper?.writableDatabase?.delete(
                    GuideContract.LocalEntry.TABLE_NAME,
                    selection1,
                    selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }

        context?.contentResolver?.notifyChange(uri, null)
        return numRowsDeleted ?: 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val numRowsDeleted: Int?

        when (URI_MATCHER.match(uri)) {
            CODE_LOCAL_WITH_ID -> {
                val id = uri.pathSegments[1]
                numRowsDeleted = mOpenHelper?.writableDatabase?.update(
                        GuideContract.LocalEntry.TABLE_NAME,
                        values,
                        GuideContract.LocalEntry._ID + " = ?",
                        arrayOf(id))
            }
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }

        return numRowsDeleted ?: 0
    }

    @TargetApi(11)
    override fun shutdown() {
        mOpenHelper?.close()
        super.shutdown()
    }

    companion object {
        val CODE_LOCAL = 100
        val CODE_LOCAL_WITH_ID = 101
        private val TAG = GuideProvider::class.java.name
        private val URI_MATCHER = buildUriMatcher()
        fun buildUriMatcher(): UriMatcher {
            val matcher = UriMatcher(UriMatcher.NO_MATCH)
            val authority = GuideContract.CONTENT_AUTHORITY
            matcher.addURI(authority, GuideContract.PATH_LOCAL, CODE_LOCAL)
            matcher.addURI(authority, GuideContract.PATH_LOCAL + "/#", CODE_LOCAL_WITH_ID)
            return matcher
        }
    }
}
