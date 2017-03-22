package com.guideapp.guideapp.sync;

import android.app.IntentService;
import android.content.Intent;

public class GuideSyncIntentService extends IntentService {

    public GuideSyncIntentService() {
        super("GuideSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GuideSyncTask.syncGuide(this);
    }
}