package com.guideapp.guideapp.UI.activities.local;

import android.support.annotation.NonNull;

import com.guideapp.guideapp.model.Local;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LocalContract {

    interface View {
        void showLocals(List<Local> locals);

        void showLocalDetailUi(Local local);
    }

    interface UserActionsListener {
        void loadLocals(boolean forceUpdate);

        void openNoteDetails(@NonNull Local local);
    }
}
