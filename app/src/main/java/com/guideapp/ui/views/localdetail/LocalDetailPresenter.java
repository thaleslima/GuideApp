package com.guideapp.ui.views.localdetail;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.guideapp.data.local.GuideContract;
import com.guideapp.model.Local;
import com.guideapp.utilities.DataUtil;
import com.guideapp.utilities.Utility;

import java.util.List;

class LocalDetailPresenter implements LocalDetailContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_LOADER = 301;

    private final LocalDetailContract.View mView;
    private final long mIdLocal;

    private String mTextToShare;
    private String mPhoneToCall;
    private String mLatLngToDirection;
    private String mSiteToOpen;
    private String mDescription;

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
    public void destroy(LoaderManager loaderManager) {
        loaderManager.destroyLoader(ID_LOADER);
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

    @Override
    public void shareLocal() {
        if (mTextToShare != null) {
            mView.shareText(mTextToShare);
        }
    }

    @Override
    public void loadWebsite() {
        if (mSiteToOpen != null) {
            mView.openPage(mSiteToOpen);
        }
    }

    @Override
    public void loadCall() {
        if (mPhoneToCall != null) {
            mView.dialPhoneNumber(mPhoneToCall);
        }
    }

    @Override
    public void loadDirection() {
        if (mLatLngToDirection != null) {
            mView.openDirection(mDescription, mLatLngToDirection);
        }
    }

    private void updateFavorite(boolean isFavorite) {
        ContentValues values = new ContentValues();
        values.put(GuideContract.LocalEntry.COLUMN_FAVORITE, isFavorite);
        mView.getContext().getContentResolver().update(GuideContract.LocalEntry.buildLocalUriWithId(mIdLocal),
                values, null, null);

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
            Local local = locals.get(0);

            mView.showTitle(local.getDescription());
            mView.showImage(local.getImagePath());
            mIsFavorite = local.isFavorite();

            if (mIsFavorite) {
                mView.showFavoriteYes();
            } else {
                mView.showFavoriteNo();
            }

            mView.showCategory(local.getDescriptionSubCategories());
            mView.showDirectionAction();

            if (!TextUtils.isEmpty(local.getDetail())) {
                mView.showDetail(local.getDetail());
            }

            if (!TextUtils.isEmpty(local.getPhone())) {
                mView.showCall(local.getPhone());
                mView.showCallAction();
            }

            if (!TextUtils.isEmpty(local.getSite())) {
                mView.showWebSiteAction();
            }

            if (!TextUtils.isEmpty(local.getAddress())) {
                mView.showAddress(local.getAddress());
            }

            mPhoneToCall = local.getPhone();
            mLatLngToDirection = local.getLatitude() + "," + local.getLongitude();
            mDescription = local.getDescription();

            mSiteToOpen = local.getSite();
            mTextToShare = Utility.getTextToShare(mView.getContext(), local);

            mView.showMap(local.getLatitude(), local.getLongitude(), Utility.getIdImageCategory(local.getIdCategory()));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
