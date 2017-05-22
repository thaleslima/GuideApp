package com.guideapp.data.local

import android.net.Uri
import android.provider.BaseColumns

import com.google.common.collect.ImmutableList

object GuideContract {
    const val CONTENT_AUTHORITY = "com.guideapp"
    const val PATH_LOCAL = "local"

    private val BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)

    class LocalEntry : BaseColumns {
        companion object {
            @JvmField val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCAL).build()!!

            const val TABLE_NAME = "local"

            const val _ID = "_id"
            const val COLUMN_DESCRIPTION = "description"
            const val COLUMN_SITE = "site"
            const val COLUMN_PHONE = "phone"
            const val COLUMN_ADDRESS = "address"
            const val COLUMN_WIFI = "wifi"
            const val COLUMN_DETAIL = "detail"
            const val COLUMN_LATITUDE = "latitude"
            const val COLUMN_LONGITUDE = "longitude"
            const val COLUMN_IMAGE_PATH = "imagePath"
            const val COLUMN_ID_CITY = "idCity"
            const val COLUMN_ID_CATEGORY = "idCategory"
            const val COLUMN_ID_SUB_CATEGORY = "idSubCategories"
            const val COLUMN_TIMESTAMP = "timestamp"
            const val COLUMN_DESCRIPTION_SUB_CATEGORY = "descriptionSubCategories"
            const val COLUMN_FAVORITE = "isFavorite"

            const val POSITION_ID = 0
            const val POSITION_DESCRIPTION = 1
            const val POSITION_SITE = 2
            const val POSITION_PHONE = 3
            const val POSITION_ADDRESS = 4
            const val POSITION_WIFI = 5
            const val POSITION_DETAIL = 6
            const val POSITION_LATITUDE = 7
            const val POSITION_LONGITUDE = 8
            const val POSITION_IMAGE_PATH = 9
            const val POSITION_ID_CITY = 10
            const val POSITION_ID_CATEGORY = 11
            const val POSITION_ID_SUB_CATEGORY = 12
            const val POSITION_TIMESTAMP = 13
            const val POSITION_DESCRIPTION_SUB_CATEGORY = 14
            const val POSITION_FAVORITE = 15

            @JvmField val COLUMNS = ImmutableList.of(
                    BaseColumns._ID,
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
            )!!

            @JvmStatic fun buildLocalUriWithId(id: Long): Uri {
                return CONTENT_URI.buildUpon()
                        .appendPath(java.lang.Long.toString(id))
                        .build()
            }

            @JvmStatic fun getSqlSelectForIdCategory(idCategory: Long): String {
                return GuideContract.LocalEntry.COLUMN_ID_CATEGORY + " = " + idCategory
            }

            @JvmStatic fun getSqlSelectForFavorites(): String{
                return GuideContract.LocalEntry.COLUMN_FAVORITE + " = " + 1
            }
        }
    }
}
