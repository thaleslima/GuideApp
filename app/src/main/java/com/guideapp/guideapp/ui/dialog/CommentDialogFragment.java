package com.guideapp.guideapp.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.guideapp.guideapp.R;

/**
 * Created by thales on 12/12/15.
 */
public class CommentDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflator = getActivity().getLayoutInflater().cloneInContext(getActivity());
        View view = inflator.inflate(R.layout.fragment_comment_dialog, null);
        android.app.AlertDialog.Builder builder =
                new android.app.AlertDialog.Builder(getActivity());
        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
