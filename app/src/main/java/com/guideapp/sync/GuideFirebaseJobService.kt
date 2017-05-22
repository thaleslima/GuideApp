package com.guideapp.sync

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class GuideFirebaseJobService : JobService() {
    private var mFetchGuideTask: AsyncTask<Void, Void, Void>? = null

    @SuppressLint("StaticFieldLeak")
    override fun onStartJob(jobParameters: JobParameters): Boolean {

        mFetchGuideTask = object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val context = applicationContext
                GuideSyncTask.syncGuide(context)
                jobFinished(jobParameters, false)
                return null
            }

            override fun onPostExecute(aVoid: Void) {
                jobFinished(jobParameters, false)
            }
        }

        mFetchGuideTask?.execute()
        return true
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        mFetchGuideTask?.cancel(true)
        return true
    }
}