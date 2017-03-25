package com.guideapp.guideapp.ui.views.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.widget.ImageView;

import com.guideapp.guideapp.model.Local;

import java.util.List;

interface LocalContract {
    interface View {
        void showLocals(List<Local> locals);

        void showLocalDetailUi(Local local, ImageView view);

        void showProgressBar();

        void hideProgressBar();

        Context getContext();
    }

    interface Presenter {
        void loadLocals(LoaderManager loaderManager);

        void restartLoadLocals(LoaderManager loaderManager, long idCategory);

        void openLocalDetails(@NonNull Local local, ImageView view);
    }
}
