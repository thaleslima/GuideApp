package com.guideapp.guideapp.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class GuideFirebaseJobService extends JobService {
    private AsyncTask<Void, Void, Void> mFetchGuideTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchGuideTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                GuideSyncTask.syncGuide(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchGuideTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchGuideTask != null) {
            mFetchGuideTask.cancel(true);
        }
        return true;
    }
}