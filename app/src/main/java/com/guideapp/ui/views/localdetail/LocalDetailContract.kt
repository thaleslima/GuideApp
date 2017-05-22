package com.guideapp.ui.views.localdetail

import android.content.Context
import android.support.v4.app.LoaderManager

internal interface LocalDetailContract {
    interface View {
        fun showFavoriteYes()

        fun showFavoriteNo()

        fun showSnackbarRemoveFavorite()

        fun showSnackbarSaveFavorite()

        fun shareText(textToShare: String)

        fun getContext(): Context

        fun showTitle(description: String)

        fun showImage(imagePath: String)

        fun showCategory(text: String)

        fun showWebSiteAction()

        fun showDirectionAction()

        fun showCallAction()

        fun showCall(phone: String)

        fun showDetail(description: String)

        fun showAddress(address: String)

        fun showMap(latitude: Double, longitude: Double, idImageMarker: Int)

        fun dialPhoneNumber(number: String)

        fun openPage(url: String)

        fun openDirection(description: String, latLng: String)
    }

    interface Presenter {
        fun loadLocal(loaderManager: LoaderManager)

        fun destroy(loaderManager: LoaderManager)

        fun saveOrRemoveFavorite()

        fun shareLocal()

        fun loadWebsite()

        fun loadCall()

        fun loadDirection()
    }
}
