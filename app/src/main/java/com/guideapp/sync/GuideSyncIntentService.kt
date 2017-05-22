package com.guideapp.sync

import android.app.IntentService
import android.content.Intent

class GuideSyncIntentService : IntentService("GuideSyncIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        GuideSyncTask.syncGuide(this)
    }
}