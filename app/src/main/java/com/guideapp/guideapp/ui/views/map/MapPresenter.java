package com.guideapp.guideapp.ui.views.map;

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

public class MapPresenter implements MapContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MapPresenter.class.getName();
    private static final int ID_LOADER = 256;
    private final MapContract.View mView;

    public MapPresenter(MapContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadLocals(LoaderManager loaderManager) {
        loaderManager.initLoader(ID_LOADER, null, this);
    }

    @Override
    public void openLocalSummary(Local local) {
        mView.showLocalSummary(local);
    }

    @Override
    public void openLocalDetails(@NonNull Local local, ImageView view) {
        mView.showLocalDetailUi(local, view);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_LOADER:
                return new CursorLoader(mView.getContext(),
                        GuideContract.LocalEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mView.showLocals(DataUtil.getLocalsFromCursor(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.showLocals(new ArrayList<>());
    }
}
