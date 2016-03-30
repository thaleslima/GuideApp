package com.guideapp.guideapp.ui.views.local;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.SubCategory;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LocalContract {

    /**
     * Interface ViewFragment
     */
    interface ViewActivity {
        /**
         * Show filters
         * @param subCategories Sub categories list
         */
        void showFilter(List<SubCategory> subCategories);

        void showMap();
    }

    /**
     * Interface UserActionsFragmentListener
     */
    interface UserActionsActivityListener {
        /**
         * Load filters
         * @param idCategory Id category
         */
        void loadFilters(long idCategory);

        void loadMap();

        /**
         * Unsubscribe RX
         */
        void unsubscribe();
    }





    /**
     * Interface ViewFragment
     */
    interface ViewFragment {
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
     * Interface UserActionsFragmentListener
     */
    interface UserActionsFragmentListener {

        /**
         * Load locals
         * @param idCity Id city
         * @param idCategory Id category
         * @param idSubCategory Id Sub categories
         */
        void loadLocals(long idCity, long idCategory, long[] idSubCategory);

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
