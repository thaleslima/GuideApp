package com.guideapp.guideapp.ui.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.ui.views.localdetail.LocalDetailActivity;

public class LocalFavoriteWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {
            private Cursor data =   null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(GuideContract.LocalEntry.CONTENT_URI,
                        GuideContract.LocalEntry.COLUMNS.toArray(new String[]{}),
                        GuideContract.LocalEntry.getSqlSelectForFavorites(),
                        null,
                        null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_detail_list_item);

                views.setTextViewText(R.id.local_text, data.getString(GuideContract.LocalEntry.POSITION_DESCRIPTION));
                views.setTextViewText(R.id.local_address, data.getString(GuideContract.LocalEntry.POSITION_ADDRESS));
                views.setTextViewText(R.id.descriptions_sub_category, data.getString(GuideContract.LocalEntry.POSITION_DESCRIPTION_SUB_CATEGORY));

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(LocalDetailActivity.EXTRA_LOCAL_ID, getItemId(position));
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

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
                if (data.moveToPosition(position))
                    return data.getLong(GuideContract.LocalEntry.POSITION_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
