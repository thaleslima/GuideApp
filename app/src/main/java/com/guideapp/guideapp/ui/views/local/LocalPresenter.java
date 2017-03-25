package com.guideapp.guideapp.ui.views.local;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ImageView;

import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.utilities.DataUtil;

import java.util.ArrayList;

class LocalPresenter implements LocalContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_LOADER = 356;

    private final LocalContract.View mView;
    private long mIdCategory;

    LocalPresenter(LocalContract.View localView, long idCategory) {
        this.mView = localView;
        this.mIdCategory = idCategory;
    }

    @Override
    public void loadLocals(LoaderManager loaderManager) {
        mView.showProgressBar();
        loaderManager.initLoader(ID_LOADER, null, this);
    }

    @Override
    public void restartLoadLocals(LoaderManager loaderManager, long idCategory) {
        if (this.mIdCategory == 0) {
            loaderManager.destroyLoader(ID_LOADER);
            this.mIdCategory = idCategory;
            loadLocals(loaderManager);
        }
    }

    @Override
    public void openLocalDetails(@NonNull Local local, ImageView view) {
        this.mView.showLocalDetailUi(local, view);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_LOADER:
                return new CursorLoader(mView.getContext(),
                        GuideContract.LocalEntry.CONTENT_URI,
                        null,
                        GuideContract.LocalEntry.getSqlSelectForIdCategory(mIdCategory),
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mView.hideProgressBar();
        mView.showLocals(DataUtil.getLocalsFromCursor(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.showLocals(new ArrayList<Local>());
        mView.hideProgressBar();
    }
}
