package com.guideapp.guideapp.ui.activities.local;

import android.support.annotation.NonNull;

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
         */
        void showLocalDetailUi(Local local);
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
         */
        void openLocalDetails(@NonNull Local local);
    }
}
