package com.guideapp.ui.views.localdetail

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.text.TextUtils

import com.guideapp.data.local.GuideContract
import com.guideapp.model.Local
import com.guideapp.utilities.DataUtil
import com.guideapp.utilities.Utility

internal class LocalDetailPresenter(private val mView: LocalDetailContract.View, private val mIdLocal: Long) : LocalDetailContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private var mTextToShare: String? = null
    private var mPhoneToCall: String? = null
    private var mLatLngToDirection: String? = null
    private var mSiteToOpen: String? = null
    private var mDescription: String? = null
    private var mIsFavorite: Boolean = false

    override fun loadLocal(loaderManager: LoaderManager) {
        loaderManager.initLoader(ID_LOADER, Bundle.EMPTY, this)
    }

    override fun destroy(loaderManager: LoaderManager) {
        loaderManager.destroyLoader(ID_LOADER)
    }

    override fun saveOrRemoveFavorite() {
        updateFavorite(!mIsFavorite)

        if (mIsFavorite) {
            mView.showFavoriteYes()
            mView.showSnackbarSaveFavorite()
        } else {
            mView.showFavoriteNo()
            mView.showSnackbarRemoveFavorite()
        }
    }

    override fun shareLocal() {
        if (mTextToShare != null) {
            mView.shareText(mTextToShare!!)
        }
    }

    override fun loadWebsite() {
        if (mSiteToOpen != null) {
            mView.openPage(mSiteToOpen!!)
        }
    }

    override fun loadCall() {
        if (mPhoneToCall != null) {
            mView.dialPhoneNumber(mPhoneToCall!!)
        }
    }

    override fun loadDirection() {
        if (mLatLngToDirection != null) {
            mView.openDirection(mDescription!!, mLatLngToDirection!!)
        }
    }

    private fun updateFavorite(isFavorite: Boolean) {
        val values = ContentValues()
        values.put(GuideContract.LocalEntry.COLUMN_FAVORITE, isFavorite)
        mView.getContext().contentResolver.update(GuideContract.LocalEntry.buildLocalUriWithId(mIdLocal),
                values, null, null)

        mIsFavorite = isFavorite

        Utility.updateWidgets(mView.getContext())
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {
        when (id) {

            ID_LOADER -> return CursorLoader(mView.getContext(),
                    GuideContract.LocalEntry.buildLocalUriWithId(mIdLocal), null, null, null, null)

            else -> throw RuntimeException("Loader Not Implemented: " + id)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        val locals = DataUtil.getLocalsFromCursor(data)

        if (locals.size > 0) {
            val local = locals[0]

            mView.showTitle(local.description!!)
            mView.showImage(local.imagePath!!)
            mIsFavorite = local.isFavorite

            if (mIsFavorite) {
                mView.showFavoriteYes()
            } else {
                mView.showFavoriteNo()
            }

            mView.showCategory(local.descriptionSubCategories!!)
            mView.showDirectionAction()

            if (!TextUtils.isEmpty(local.detail)) {
                mView.showDetail(local.detail!!)
            }

            if (!TextUtils.isEmpty(local.phone)) {
                mView.showCall(local.phone!!)
                mView.showCallAction()
            }

            if (!TextUtils.isEmpty(local.site)) {
                mView.showWebSiteAction()
            }

            if (!TextUtils.isEmpty(local.address)) {
                mView.showAddress(local.address!!)
            }

            mPhoneToCall = local.phone
            mLatLngToDirection = local.latitude.toString() + "," + local.longitude
            mDescription = local.description

            mSiteToOpen = local.site
            mTextToShare = Utility.getTextToShare(mView.getContext(), local)

            mView.showMap(local.latitude, local.longitude, Utility.getIdImageCategory(local.idCategory))
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    companion object {
        private val ID_LOADER = 301
    }
}
