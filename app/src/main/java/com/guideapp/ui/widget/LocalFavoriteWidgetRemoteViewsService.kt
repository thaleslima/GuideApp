package com.guideapp.ui.widget

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.util.Log
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.guideapp.R
import com.guideapp.data.local.GuideContract
import com.guideapp.ui.views.localdetail.LocalDetailActivity

import java.util.concurrent.ExecutionException

class LocalFavoriteWidgetRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return object : RemoteViewsService.RemoteViewsFactory {
            private var mData: Cursor? = null

            override fun onCreate() {

            }

            override fun onDataSetChanged() {
                mData?.close()

                val identityToken = Binder.clearCallingIdentity()
                mData = contentResolver.query(GuideContract.LocalEntry.CONTENT_URI,
                        GuideContract.LocalEntry.COLUMNS.toTypedArray(),
                        GuideContract.LocalEntry.getSqlSelectForFavorites(), null, null)
                Binder.restoreCallingIdentity(identityToken)
            }

            override fun onDestroy() {
                mData?.close()
                mData = null
            }

            override fun getCount(): Int {
                return mData?.count ?: 0
            }

            override fun getViewAt(position: Int): RemoteViews? {
                if (position == AdapterView.INVALID_POSITION || mData == null || !mData!!.moveToPosition(position)) {
                    return null
                }

                val views = RemoteViews(packageName, R.layout.widget_detail_list_item)
                views.setTextViewText(R.id.local_text, mData?.getString(GuideContract.LocalEntry.POSITION_DESCRIPTION))
                views.setTextViewText(R.id.descriptions_sub_category, mData?.getString(GuideContract.LocalEntry.POSITION_DESCRIPTION_SUB_CATEGORY))
                val fillInIntent = Intent()
                fillInIntent.putExtra(LocalDetailActivity.EXTRA_LOCAL_ID, getItemId(position))
                fillInIntent.putExtra(LocalDetailActivity.EXTRA_CATEGORY_ID, mData?.getLong(GuideContract.LocalEntry.POSITION_ID_CATEGORY))
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent)

                var image: Bitmap? = null
                val urlImage = mData?.getString(GuideContract.LocalEntry.POSITION_IMAGE_PATH)
                views.setImageViewResource(R.id.local_picture, R.color.placeholder)

                try {
                    image = Glide.with(this@LocalFavoriteWidgetRemoteViewsService)
                            .load(urlImage)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
                } catch (e: InterruptedException) {
                    Log.e(TAG, "Error retrieving large icon from " + urlImage, e)
                } catch (e: ExecutionException) {
                    Log.e(TAG, "Error retrieving large icon from " + urlImage, e)
                }

                if (image != null) {
                    views.setImageViewBitmap(R.id.local_picture, image)
                } else {
                    views.setImageViewResource(R.id.local_picture, R.color.placeholder)
                }

                return views
            }

            override fun getLoadingView(): RemoteViews {
                return RemoteViews(packageName, R.layout.widget_detail_list_item)
            }

            override fun getViewTypeCount(): Int {
                return 1
            }

            override fun getItemId(position: Int): Long {
                mData?.let { data ->
                    if (data.moveToPosition(position))
                        return data.getLong(GuideContract.LocalEntry.POSITION_ID)

                }
                return position.toLong()
            }

            override fun hasStableIds(): Boolean {
                return true
            }
        }
    }

    companion object {
        private val TAG = LocalFavoriteWidgetRemoteViewsService::class.java.simpleName
    }
}
