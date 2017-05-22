package com.guideapp.utilities

import android.content.ContentValues
import android.database.Cursor

import com.guideapp.data.local.GuideContract
import com.guideapp.model.Local
import com.guideapp.model.SubCategory

import java.util.ArrayList
import java.util.HashSet

object DataUtil {

    fun getLocalsFavoritesFromCursor(cursor: Cursor): Set<Long> {
        val longSet = HashSet<Long>()
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(GuideContract.LocalEntry._ID)
            do {
                longSet.add(cursor.getLong(columnIndex))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return longSet
    }

    fun getLocalsFromCursor(cursor: Cursor): List<Local> {
        val locals = ArrayList<Local>()
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(GuideContract.LocalEntry._ID)
            val columnDescription = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DESCRIPTION)
            val columnSite = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_SITE)
            val columnPhone = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_PHONE)
            val columnAddress = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ADDRESS)
            val columnWifi = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_WIFI)
            val columnDetail = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DETAIL)
            val columnLatitude = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_LATITUDE)
            val columnLongitude = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_LONGITUDE)
            val columnImagePath = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_IMAGE_PATH)
            val columnIdCategory = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_CATEGORY)
            val columnIndexIdCity = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_ID_CITY)
            val columnIndexTimestamp = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_TIMESTAMP)
            val columnIndexDescriptionSubCategory = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY)
            val columnIndexFavorite = cursor.getColumnIndex(GuideContract.LocalEntry.COLUMN_FAVORITE)
            do {
                val local = Local()
                local.id = cursor.getLong(columnIndex)
                local.description = cursor.getString(columnDescription)
                local.site = cursor.getString(columnSite)
                local.phone = cursor.getString(columnPhone)
                local.address = cursor.getString(columnAddress)
                local.isWifi = cursor.getInt(columnWifi) == 1
                local.detail = cursor.getString(columnDetail)
                local.latitude = cursor.getDouble(columnLatitude)
                local.longitude = cursor.getDouble(columnLongitude)
                local.imagePath = cursor.getString(columnImagePath)
                local.idCategory = cursor.getLong(columnIdCategory)
                local.idCity = cursor.getLong(columnIndexIdCity)
                local.timestamp = cursor.getLong(columnIndexTimestamp)
                local.descriptionSubCategories = cursor.getString(columnIndexDescriptionSubCategory)
                local.isFavorite = cursor.getInt(columnIndexFavorite) == 1
                locals.add(local)
            } while (cursor.moveToNext())
        }
        return locals
    }


    fun getGuideContentValuesFromList(locals: List<Local>, favorites: Set<Long>): Array<ContentValues> {
        val contentValues = ArrayList<ContentValues>()
        for (local in locals) {
            val testValues = ContentValues()
            testValues.put(GuideContract.LocalEntry._ID, local.id)
            testValues.put(GuideContract.LocalEntry.COLUMN_DESCRIPTION, local.description)
            testValues.put(GuideContract.LocalEntry.COLUMN_SITE, local.site)
            testValues.put(GuideContract.LocalEntry.COLUMN_PHONE, local.phone)
            testValues.put(GuideContract.LocalEntry.COLUMN_ADDRESS, local.address)
            testValues.put(GuideContract.LocalEntry.COLUMN_WIFI, local.isWifi)
            testValues.put(GuideContract.LocalEntry.COLUMN_DETAIL, local.detail)
            testValues.put(GuideContract.LocalEntry.COLUMN_LATITUDE, local.latitude)
            testValues.put(GuideContract.LocalEntry.COLUMN_LONGITUDE, local.longitude)
            testValues.put(GuideContract.LocalEntry.COLUMN_IMAGE_PATH, local.imagePath)
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_CITY, local.idCity)
            testValues.put(GuideContract.LocalEntry.COLUMN_ID_CATEGORY, local.idCategories!![0])
            testValues.put(GuideContract.LocalEntry.COLUMN_TIMESTAMP, local.timestamp)
            testValues.put(GuideContract.LocalEntry.COLUMN_DESCRIPTION_SUB_CATEGORY,
                    getDescriptionFromSubCategories(local.subCategories))
            testValues.put(GuideContract.LocalEntry.COLUMN_FAVORITE, favorites.contains(local.id))

            contentValues.add(testValues)
        }
        val contentValuesArr = contentValues.toTypedArray()
        return contentValuesArr
    }

    private fun getDescriptionFromSubCategories(subCategories: List<SubCategory>?): String {
        if (subCategories != null) {
            val size = subCategories.size
            var description = ""
            for (i in 0..size - 1) {
                if (i > 0) {
                    description += " | "
                }
                description += subCategories[i].description
            }

            return description
        }
        return ""
    }
}
