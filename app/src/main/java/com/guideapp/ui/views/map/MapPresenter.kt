package com.guideapp.ui.views.map

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

internal class MapPresenter(private val mView: MapContract.View) : MapContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    override fun loadLocals(loaderManager: LoaderManager) {
        loaderManager.initLoader(ID_LOADER, Bundle.EMPTY, this)
    }

    override fun openLocalSummary(local: Local?) {
        local?.let { mView.showLocalSummary(it) }
    }

    override fun openLocalDetails(local: Local, view: ImageView) {
        mView.showLocalDetailUi(local, view)
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {
        when (id) {
            ID_LOADER -> return CursorLoader(mView.getContext(), GuideContract.LocalEntry.CONTENT_URI, null, null, null, null)
            else -> throw RuntimeException("Loader Not Implemented: " + id)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        mView.showLocals(DataUtil.getLocalsFromCursor(data))
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mView.showLocals(ArrayList<Local>())
    }

    companion object {
        private val ID_LOADER = 256
    }
}
