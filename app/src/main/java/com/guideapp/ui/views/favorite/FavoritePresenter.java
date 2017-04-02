package com.guideapp.ui.views.favorite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ImageView;

import com.guideapp.data.local.GuideContract;
import com.guideapp.model.Local;
import com.guideapp.utilities.DataUtil;

import java.util.ArrayList;

class FavoritePresenter implements FavoriteContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_LOADER = 333;
    private final FavoriteContract.View mView;

    FavoritePresenter(FavoriteContract.View localView) {
        this.mView = localView;
    }

    @Override
    public void loadLocals(LoaderManager loaderManager) {
        loaderManager.initLoader(ID_LOADER, null, this);
    }

    @Override
    public void onResume(LoaderManager loaderManager) {
        loaderManager.restartLoader(ID_LOADER, null, this);
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
                        GuideContract.LocalEntry.getSqlSelectForFavorites(),
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            mView.showLocals(DataUtil.getLocalsFromCursor(data));
            mView.hideNoItemsMessage();
        } else {
            mView.showLocals(new ArrayList<Local>());
            mView.showNoItemsMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.showLocals(new ArrayList<Local>());
    }
}
