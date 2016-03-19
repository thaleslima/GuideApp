package com.guideapp.guideapp.ui.activities.local;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.guideapp.guideapp.model.Local;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LocalContract {
    /**
     * Interface View
     */
    interface View {
        /**
         * Show locals
         * @param locals Local list
         */
        void showLocals(List<Local> locals);

        /**
         * Show local detail
         * @param local Local object
         * @param view Transition image
         */
        void showLocalDetailUi(Local local, ImageView view);

        /**
         * Show progress bar
         */
        void showProgressBar();

        /**
         * Hide progress bar
         */
        void hideProgressBar();
    }

    /**
     * Interface UserActionsListener
     */
    interface UserActionsListener {

        /**
         * Load locals
         * @param forceUpdate Force update data
         */
        void loadLocals(boolean forceUpdate);

        /**
         * Open local details
         * @param local Local object
         * @param view Transition image
         */
        void openLocalDetails(@NonNull Local local, ImageView view);

        /**
         * Unsubscribe RX
         */
        void unsubscribe();
    }
}
