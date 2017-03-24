package com.guideapp.guideapp.ui.views.localdetail;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.LocalDetail;
import com.guideapp.guideapp.utilities.DataUtil;
import com.guideapp.guideapp.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

class LocalDetailPresenter implements LocalDetailContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_LOADER = 301;

    private final LocalDetailContract.View mView;
    private final long mIdLocal;
    private boolean mIsFavorite;

    LocalDetailPresenter(LocalDetailContract.View localView, long idLocal) {
        this.mView = localView;
        this.mIdLocal = idLocal;
    }

    @Override
    public void loadLocal(LoaderManager loaderManager) {
        loaderManager.initLoader(ID_LOADER, null, this);
    }

    @Override
    public void saveOrRemoveFavorite() {
        updateFavorite(!mIsFavorite);

        if (mIsFavorite) {
            mView.showFavoriteYes();
            mView.showSnackbarSaveFavorite();
        } else {
            mView.showFavoriteNo();
            mView.showSnackbarRemoveFavorite();
        }
    }

    private void updateFavorite(boolean isFavorite) {
        ContentValues values = new ContentValues();
        values.put(GuideContract.LocalEntry.COLUMN_FAVORITE, isFavorite);
        mView.getContext().getContentResolver().update(GuideContract.LocalEntry.buildLocalUriWithId(mIdLocal), values, null, null);

        mIsFavorite = isFavorite;

        Utility.updateWidgets(mView.getContext());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_LOADER:
                return new CursorLoader(mView.getContext(),
                        GuideContract.LocalEntry.buildLocalUriWithId(mIdLocal),
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
        List<Local> locals = DataUtil.getLocalsFromCursor(data);

        if (locals.size() > 0) {
            mView.showTitle(locals.get(0).getDescription());
            mView.showImage(locals.get(0).getImagePath());

            mIsFavorite = locals.get(0).isFavorite();

            if (mIsFavorite) {
                mView.showFavoriteYes();
            } else {
                mView.showFavoriteNo();
            }

            mView.showLocalDetail(createListLocalDetail(locals.get(0)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private static List<LocalDetail> createListLocalDetail(Local local) {
        List<LocalDetail> list = new ArrayList<>();

        if (!TextUtils.isEmpty(local.getDescriptionSubCategories())) {
            list.add(new LocalDetail(R.drawable.ic_info_grey_72_24dp,
                    local.getDescriptionSubCategories(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!TextUtils.isEmpty(local.getSite())) {
            list.add(new LocalDetail(R.drawable.ic_language_grey_72_24dp,
                    local.getSite(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!TextUtils.isEmpty(local.getPhone())) {
            list.add(new LocalDetail(R.drawable.ic_call_grey_72_24dp,
                    local.getPhone(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!TextUtils.isEmpty(local.getDetail())) {
            list.add(new LocalDetail(R.drawable.ic_apps_grey_72_24dp,
                    local.getDetail(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!TextUtils.isEmpty(local.getAddress())) {
            list.add(new LocalDetail(R.drawable.ic_location_on_grey_72_24dp,
                    local.getAddress(), false,
                    LocalDetailAdapter.LOCAL_DETAIL));
        }

        list.add(new LocalDetail(true, LocalDetailAdapter.LOCAL_DETAIL_MAP,
                local.getLatitude(), local.getLongitude(), Utility.getIdImageCategory(local.getIdCategory())));


        return list;
    }
}
