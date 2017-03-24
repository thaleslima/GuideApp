package com.guideapp.guideapp.ui.views.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.widget.ImageView;

import com.guideapp.guideapp.model.Local;

import java.util.List;

interface MapContract {
    interface View {
        void showLocals(List<Local> locals);

        void showLocalSummary(Local local);

        void showLocalDetailUi(Local local, ImageView view);

        Context getContext();
    }

    interface Presenter {
        void loadLocals(LoaderManager loaderManager);

        void openLocalSummary(Local local);

        void openLocalDetails(@NonNull Local local, ImageView view);
    }
}
