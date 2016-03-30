package com.guideapp.guideapp.ui.views.map;

import com.guideapp.guideapp.model.Local;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MapContract {
    /**
     * Interface ViewFragment
     */
    interface View {
        /**
         * Show locals
         * @param locals Local list
         */
        void showLocals(List<Local> locals);


        void showLocalSummary(Local local);

        void showLocal(Local local);
    }

    /**
     * Interface UserActionsFragmentListener
     */
    interface UserActionsListener {

        /**
         * Load locals
         * @param idCity Id city
         * @param idCategory Id category
         * @param idSubCategory Id Sub categories
         */
        void loadLocals(long idCity, long idCategory, long[] idSubCategory);


        void loadLocalSummary(Local local);


        void loadLocal(Local local);
        /**
         * Unsubscribe RX
         */
        void unsubscribe();
    }
}
