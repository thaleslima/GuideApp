package com.guideapp.ui.views.localdetail;

import android.content.Context;
import android.support.v4.app.LoaderManager;

interface LocalDetailContract {
    interface View {
        void showFavoriteYes();

        void showFavoriteNo();

        void showSnackbarRemoveFavorite();

        void showSnackbarSaveFavorite();

        void shareText(String textToShare);

        Context getContext();

        void showTitle(String description);

        void showImage(String imagePath);

        void showCategory(String text);

        void showWebSiteAction();

        void showDirectionAction();

        void showCallAction();

        void showCall(String phone);

        void showDetail(String description);

        void showAddress(String address);

        void showMap(double latitude, double longitude, int idImageMarker);

        void dialPhoneNumber(String number);

        void openPage(String url);

        void openDirection(String description, String latLng);
    }

    interface Presenter {
        void loadLocal(LoaderManager loaderManager);

        void destroy(LoaderManager loaderManager);

        void saveOrRemoveFavorite();

        void shareLocal();

        void loadWebsite();

        void loadCall();

        void loadDirection();
    }
}
