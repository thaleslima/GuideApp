package com.guideapp.ui.views.local

import android.content.Context
import android.support.v4.app.LoaderManager
import android.widget.ImageView

import com.guideapp.model.Local

internal interface LocalContract {
    interface View {
        fun showLocals(locals: List<Local>)

        fun showLocalDetailUi(local: Local, view: ImageView)

        fun showProgressBar()

        fun hideProgressBar()

        fun getContext(): Context
    }

    interface Presenter {
        fun loadLocals(loaderManager: LoaderManager)

        fun restartLoadLocals(loaderManager: LoaderManager, idCategory: Long)

        fun openLocalDetails(local: Local, view: ImageView)
    }
}
