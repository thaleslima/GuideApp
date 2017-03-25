package com.guideapp.ui.views.favorite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.widget.ImageView;

import com.guideapp.model.Local;

import java.util.List;

interface FavoriteContract {
    interface View {
        void showNoItemsMessage();

        void hideNoItemsMessage();

        void showLocals(List<Local> locals);

        void showLocalDetailUi(Local local, ImageView view);

        Context getContext();
    }

    interface Presenter {
        void loadLocals(LoaderManager loaderManager);

        void onResume(LoaderManager loaderManager);

        void openLocalDetails(@NonNull Local local, ImageView view);
    }
}
