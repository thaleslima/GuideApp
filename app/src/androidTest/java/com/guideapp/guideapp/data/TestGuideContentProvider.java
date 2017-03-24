package com.guideapp.guideapp.data;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.data.local.GuideDbHelper;
import com.guideapp.guideapp.data.local.GuideProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TestGuideContentProvider {

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {
        GuideDbHelper dbHelper = new GuideDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(GuideContract.LocalEntry.TABLE_NAME, null, null);
    }

    @Test
    public void testProviderRegistry() {
        String packageName = mContext.getPackageName();
        String movieProviderClassName = GuideProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, movieProviderClassName);

        try {

            /*
             * Get a reference to the package manager. The package manager allows us to access
             * information about packages installed on a particular device. In this case, we're
             * going to use it to get some information about our ContentProvider under test.
             */
            PackageManager pm = mContext.getPackageManager();

            /* The ProviderInfo will contain the authority, which is what we want to test */
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = packageName;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: MovieProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: MovieProvider not registered at " + mContext.getPackageName();
            /*
             * This exception is thrown if the ContentProvider hasn't been registered with the
             * manifest at all. If this is the case, you need to double check your
             * AndroidManifest file
             */
            fail(providerNotRegisteredAtAll);
        }
    }

    private static final Uri TEST_MOVIES = GuideContract.LocalEntry.CONTENT_URI;

    @Test
    public void testUriMatcher() {

        UriMatcher testMatcher = GuideProvider.buildUriMatcher();

        String uriDoesNotMatch = "Error: The MOVIES URI was matched incorrectly.";
        int actualMatchCode = testMatcher.match(TEST_MOVIES);
        int expectedMatchCode = GuideProvider.CODE_LOCAL;
        assertEquals(uriDoesNotMatch,
                actualMatchCode,
                expectedMatchCode);
    }


    @Test
    public void testBulkInsert() {
        TestUtilities.TestContentObserver observer = TestUtilities.getTestContentObserver();
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.registerContentObserver(
                GuideContract.LocalEntry.CONTENT_URI,
                true,
                observer);

        ContentValues[] contentValuesArr = createContentValues();
        int total = contentResolver.bulkInsert(GuideContract.LocalEntry.CONTENT_URI, contentValuesArr);

        int expectedTotal = contentValuesArr.length;
        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, total, expectedTotal);

        observer.waitForNotificationOrFail();
        contentResolver.unregisterContentObserver(observer);
    }



    @Test
    public void testQuery() {
        GuideDbHelper dbHelper = new GuideDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues[] contentValuesArr = createContentValues();

        for (ContentValues contentValues : contentValuesArr) {
            long rowId = database.insert(
                    GuideContract.LocalEntry.TABLE_NAME,
                    null,
                    contentValues);

            String insertFailed = "Unable to insert directly into the database";
            assertTrue(insertFailed, rowId != -1);
        }

        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                GuideContract.LocalEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        String queryFailed = "Query failed to return a valid Cursor";
        assertTrue(queryFailed, cursor != null);

        int expectedTotal = contentValuesArr.length;
        int total = cursor.getCount();
        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, total, expectedTotal);


        cursor.close();
    }

    @Test
    public void testQueryById() {
        GuideDbHelper dbHelper = new GuideDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues[] contentValuesArr = createContentValues();

        for (ContentValues contentValues : contentValuesArr) {
            long rowId = database.insert(
                    GuideContract.LocalEntry.TABLE_NAME,
                    null,
                    contentValues);

            String insertFailed = "Unable to insert directly into the database";
            assertTrue(insertFailed, rowId != -1);
        }

        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                GuideContract.LocalEntry.buildLocalUriWithId(1),
                null,
                null,
                null,
                null);

        int expectedTotal = 1;
        assert cursor != null;
        int total = cursor.getCount();
        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, total, expectedTotal);

        if (cursor.moveToFirst()) {
            int columnIndex0 = cursor.getColumnIndex(GuideContract.LocalEntry._ID);
            int columnIndex1 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DESCRIPTION);
            int columnIndex2 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_SITE);
            int columnIndex3 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_PHONE);
            int columnIndex4 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ADDRESS);
            int columnIndex5 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_WIFI);
            int columnIndex6 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DETAIL);
            int columnIndex7 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_LATITUDE);
            int columnIndex8 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_LONGITUDE);
            int columnIndex9 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_IMAGE_PATH);
            int columnIndex10 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_CITY);
            int columnIndex11 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_CATEGORY);
            int columnIndex12 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_SUB_CATEGORY);
            int columnIndex13 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_TIMESTAMP);
            int columnIndex14 = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY);

            int data0 = cursor.getInt(columnIndex0);
            String data1 = cursor.getString(columnIndex1);
            String data2 = cursor.getString(columnIndex2);
            String data3 = cursor.getString(columnIndex3);
            String data4 = cursor.getString(columnIndex4);
            int data5 = cursor.getInt(columnIndex5);
            String data6 = cursor.getString(columnIndex6);
            double data7 = cursor.getDouble(columnIndex7);
            double data8 = cursor.getDouble(columnIndex8);
            String data9 = cursor.getString(columnIndex9);
            int data10 = cursor.getInt(columnIndex10);
            int data11 = cursor.getInt(columnIndex11);
            String data12 = cursor.getString(columnIndex12);
            int data13 = cursor.getInt(columnIndex13);
            String data14 = cursor.getString(columnIndex14);

            assertEquals(1, data0);
            assertEquals("DESCRIPTION1", data1);
            assertEquals("SITE1", data2);
            assertEquals("PHONE1", data3);
            assertEquals("ADDRESS1", data4);
            assertEquals(1, data5);
            assertEquals("DETAIL1", data6);
            assertEquals(1, (int) data7);
            assertEquals(1, (int) data8);
            assertEquals("IMAGE_PATH1", data9);
            assertEquals(1, data10);
            assertEquals(10, data11);
            assertEquals("ID_SUB_CATEGORY1", data12);
            assertEquals(1, data13);
            assertEquals("DESCRIPTION_SUB_CATEGORY1", data14);
        }

        cursor.close();
    }

    @Test
    public void testQueryByIdCategory() {
        GuideDbHelper dbHelper = new GuideDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues[] contentValuesArr = createContentValues();

        for (ContentValues contentValues : contentValuesArr) {
            long rowId = database.insert(
                    GuideContract.LocalEntry.TABLE_NAME,
                    null,
                    contentValues);

            String insertFailed = "Unable to insert directly into the database";
            assertTrue(insertFailed, rowId != -1);
        }

        database.close();
        Cursor cursor = mContext.getContentResolver().query(
                null,
                null,
                null,
                null,
                null);

        int expectedTotal = contentValuesArr.length;
        assert cursor != null;
        int total = cursor.getCount();
        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, total, expectedTotal);

        cursor.close();
    }


    private ContentValues[] createContentValues() {
        List<ContentValues> contentValues = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ContentValues testValues = new ContentValues();
            testValues.put(GuideContract.LocalEntry._ID, i);
            testValues.put(GuideContract.LocalEntry.COLUMN_DESCRIPTION, "DESCRIPTION" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_SITE, "SITE" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_PHONE, "PHONE" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_ADDRESS, "ADDRESS" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_WIFI, i);
            testValues.put(GuideContract.LocalEntry.COLUMN_DETAIL, "DETAIL" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_LATITUDE, i);
            testValues.put(GuideContract.LocalEntry.COLUMN_LONGITUDE, i);
            testValues.put(GuideContract.LocalEntry.COLUMN_IMAGE_PATH, "IMAGE_PATH" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_CITY, i);
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_CATEGORY, 10);
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_SUB_CATEGORY, "ID_SUB_CATEGORY" + i);
            testValues.put(GuideContract.LocalEntry.COLUMN_TIMESTAMP, i);
            testValues.put(GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY, "DESCRIPTION_SUB_CATEGORY" + i);

            contentValues.add(testValues);
        }

        ContentValues[] contentValuesArr = new ContentValues[contentValues.size()];
        contentValuesArr = contentValues.toArray(contentValuesArr);

        return contentValuesArr;
    }

}
