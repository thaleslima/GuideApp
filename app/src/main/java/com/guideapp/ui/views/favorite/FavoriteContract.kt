package com.guideapp.ui.views.favorite

import android.content.Context
import android.support.v4.app.LoaderManager
import android.widget.ImageView

import com.guideapp.model.Local

internal interface FavoriteContract {
    interface View {
        fun showNoItemsMessage()

        fun hideNoItemsMessage()

        fun showLocals(locals: List<Local>)

        fun showLocalDetailUi(local: Local, view: ImageView)

        fun getContext(): Context
    }

    interface Presenter {
        fun loadLocals(loaderManager: LoaderManager)

        fun onResume(loaderManager: LoaderManager)

        fun openLocalDetails(local: Local, view: ImageView)
    }
}
