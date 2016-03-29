package com.guideapp.guideapp.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 12/12/15.
 */
public class FilterDialogFragment extends DialogFragment {
    private List<SubCategory> mSubCategories;
    private long[] mIdSubCategories;

    private static final String EXTRA_ITEMS = "items";
    private static final String EXTRA_ID_SUB_CATEGORIES = "id-sub-categories";

    private FilterDialogPositiveListener mListener;

    private List<Long> mSelectedItems;
    /**
     * New Instance to filter dialog fragment
     *
     * @param subCategories Sub category list
     * @param idSubCategories Sub category checked list
     * @return FilterDialogFragment instance
     */
    public static FilterDialogFragment newInstance(List<SubCategory> subCategories, long[] idSubCategories) {
        FilterDialogFragment newFragment = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_ITEMS, (ArrayList<? extends Parcelable>) subCategories);
        bundle.putLongArray(EXTRA_ID_SUB_CATEGORIES, idSubCategories);

        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_filter);

        initExtra();

        CharSequence[] items = null;
        boolean[] checkedItems = null;

        if (mSubCategories != null) {
            int size = mSubCategories.size();
            items = new CharSequence[size];
            checkedItems = new boolean[size];
            int sizeCheckedItems = 0;

            if (mIdSubCategories != null) {
                sizeCheckedItems = mIdSubCategories.length;
            }

            for (int i = 0; i < size; i++) {
                items[i] = mSubCategories.get(i).getDescription();
                checkedItems[i] = false;

                for (int y = 0; y < sizeCheckedItems; y++) {
                    if (mIdSubCategories[y] == mSubCategories.get(i).getId()) {
                        checkedItems[i] = true;
                        break;
                    }
                }
            }
        }

        mSelectedItems = new ArrayList<>();

        builder.setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> {
            if (isChecked) {
                mSelectedItems.add(mSubCategories.get(which).getId());
            } else if (mSelectedItems.contains(mSubCategories.get(which).getId())) {
                mSelectedItems.remove(mSubCategories.get(which).getId());
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, (dialog, id) -> {

            if(mListener != null) {
                int size = mSelectedItems.size();
                long[] idSubCategories = new long[size];

                for (int i = 0; i < size; i++) {
                    idSubCategories[i] = mSelectedItems.get(i);
                }
                mListener.onFilterDialogPositive(idSubCategories);
            }

            dialog.dismiss();

        }).setNegativeButton(R.string.dialog_cancel, (dialog, id) -> {
            dialog.dismiss();
        });

        return builder.create();
    }

    /**
     * Initialize extras parameters
     */
    private void initExtra() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSubCategories = bundle.getParcelableArrayList(EXTRA_ITEMS);
            mIdSubCategories = bundle.getLongArray(EXTRA_ID_SUB_CATEGORIES);
        }
    }

    public void show(FragmentManager manager, String tag, FilterDialogPositiveListener listener) {
        super.show(manager, tag);

        mListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when a positive view is clicked.
     */
    public interface FilterDialogPositiveListener{
        void onFilterDialogPositive(long[] idSubCategories);
    }
}
