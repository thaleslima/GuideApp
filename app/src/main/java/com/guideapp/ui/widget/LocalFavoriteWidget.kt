package com.guideapp.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v4.app.TaskStackBuilder
import android.widget.RemoteViews

import com.guideapp.R
import com.guideapp.ui.views.localdetail.LocalDetailActivity
import com.guideapp.ui.views.main.MainActivity
import com.guideapp.utilities.Constants

class LocalFavoriteWidget : AppWidgetProvider() {

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_detail)
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        views.setOnClickPendingIntent(R.id.widget, pendingIntent)
        setRemoteAdapter(context, views)
        val clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(Intent(context, LocalDetailActivity::class.java))
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate)
        views.setEmptyView(R.id.widget_list, R.id.widget_empty)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setRemoteAdapter(context: Context, views: RemoteViews) {
        views.setRemoteAdapter(R.id.widget_list,
                Intent(context, LocalFavoriteWidgetRemoteViewsService::class.java))
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (Constants.ACTION_DATA_UPDATED == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                    ComponentName(context, javaClass))
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list)
        }
    }
}

