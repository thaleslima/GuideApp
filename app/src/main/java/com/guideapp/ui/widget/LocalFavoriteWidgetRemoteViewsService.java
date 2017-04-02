package com.guideapp.ui.widget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.guideapp.R;
import com.guideapp.data.local.GuideContract;
import com.guideapp.ui.views.localdetail.LocalDetailActivity;

import java.util.concurrent.ExecutionException;

public class LocalFavoriteWidgetRemoteViewsService extends RemoteViewsService {
    private static final String TAG = LocalFavoriteWidgetRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {
            private Cursor mData =   null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (mData != null) {
                    mData.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                mData = getContentResolver().query(GuideContract.LocalEntry.CONTENT_URI,
                        GuideContract.LocalEntry.COLUMNS.toArray(new String[]{}),
                        GuideContract.LocalEntry.getSqlSelectForFavorites(),
                        null,
                        null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (mData != null) {
                    mData.close();
                    mData = null;
                }
            }

            @Override
            public int getCount() {
                return mData == null ? 0 : mData.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || mData == null
                        || !mData.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_detail_list_item);

                views.setTextViewText(R.id.local_text, mData.getString(GuideContract.LocalEntry.POSITION_DESCRIPTION));
                //views.setTextViewText(R.id.local_address, data.getString(GuideContract.LocalEntry.POSITION_ADDRESS));
                views.setTextViewText(R.id.descriptions_sub_category,
                        mData.getString(GuideContract.LocalEntry.POSITION_DESCRIPTION_SUB_CATEGORY));

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(LocalDetailActivity.EXTRA_LOCAL_ID, getItemId(position));
                fillInIntent.putExtra(LocalDetailActivity.EXTRA_CATEGORY_ID,
                        mData.getLong(GuideContract.LocalEntry.POSITION_ID_CATEGORY));

                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

                Bitmap image = null;
                String urlImage = mData.getString(GuideContract.LocalEntry.POSITION_IMAGE_PATH);

                views.setImageViewResource(R.id.local_picture, R.color.placeholder);

                try {
                    image = Glide.with(LocalFavoriteWidgetRemoteViewsService.this)
                            .load(urlImage)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                } catch (InterruptedException | ExecutionException e) {
                    Log.e(TAG, "Error retrieving large icon from " + urlImage, e);
                }

                if (image != null) {
                    views.setImageViewBitmap(R.id.local_picture, image);
                } else {
                    views.setImageViewResource(R.id.local_picture, R.color.placeholder);
                }

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (mData.moveToPosition(position))
                    return mData.getLong(GuideContract.LocalEntry.POSITION_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
