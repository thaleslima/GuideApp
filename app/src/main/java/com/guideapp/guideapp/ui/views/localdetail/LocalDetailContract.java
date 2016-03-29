package com.guideapp.guideapp.ui.views.localdetail;

import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.LocalDetail;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LocalDetailContract {
    /**
     * Interface ViewFragment
     */
    interface View {
        /**
         * Show local
         * @param localDetails Local list
         */
        void showLocalDetail(List<LocalDetail> localDetails);

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
     * Interface UserActionsFragmentListener
     */
    interface UserActionsListener {

        /**
         * Load local
         * @param local Local list
         */
        void loadLocal(Local local);

        /**
         * Unsubscribe RX
         */
        void unsubscribe();
    }
}
