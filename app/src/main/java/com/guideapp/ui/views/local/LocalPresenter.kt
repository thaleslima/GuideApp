package com.guideapp.ui.views.local

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.widget.ImageView

import com.guideapp.data.local.GuideContract
import com.guideapp.model.Local
import com.guideapp.utilities.DataUtil

import java.util.ArrayList

internal class LocalPresenter(private val mView: LocalContract.View, private var mIdCategory: Long) : LocalContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    override fun loadLocals(loaderManager: LoaderManager) {
        mView.showProgressBar()
        loaderManager.initLoader(ID_LOADER, Bundle.EMPTY, this)
    }

    override fun restartLoadLocals(loaderManager: LoaderManager, idCategory: Long) {
        if (this.mIdCategory == 0L) {
            loaderManager.destroyLoader(ID_LOADER)
            this.mIdCategory = idCategory
            loadLocals(loaderManager)
        }
    }

    override fun openLocalDetails(local: Local, view: ImageView) {
        this.mView.showLocalDetailUi(local, view)
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {
        when (id) {
            ID_LOADER -> return CursorLoader(mView.getContext(),
                    GuideContract.LocalEntry.CONTENT_URI, null,
                    GuideContract.LocalEntry.getSqlSelectForIdCategory(mIdCategory), null, null)
            else -> throw RuntimeException("Loader Not Implemented: " + id)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        mView.hideProgressBar()
        mView.showLocals(DataUtil.getLocalsFromCursor(data))
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mView.showLocals(ArrayList<Local>())
        mView.hideProgressBar()
    }

    companion object {
        private val ID_LOADER = 356
    }
}
