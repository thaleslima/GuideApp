package com.guideapp.ui.views.map

import android.content.Context
import android.support.v4.app.LoaderManager
import android.widget.ImageView

import com.guideapp.model.Local

internal interface MapContract {
    interface View {
        fun showLocals(locals: List<Local>)

        fun showLocalSummary(local: Local)

        fun showLocalDetailUi(local: Local, view: ImageView)

        fun getContext() : Context
    }

    interface Presenter {
        fun loadLocals(loaderManager: LoaderManager)

        fun openLocalSummary(local: Local?)

        fun openLocalDetails(local: Local, view: ImageView)
    }
}
