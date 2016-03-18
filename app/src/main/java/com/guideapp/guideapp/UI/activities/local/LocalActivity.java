package com.guideapp.guideapp.ui.activities.local;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.activities.BaseActivity;
import com.guideapp.guideapp.ui.fragments.FilterDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Local Activity
 */
public class LocalActivity extends BaseActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.button_filter) ImageView mButtonFilter;

    /**
     * Navigate to local activity
     * @param context The Context the view is running in
     */
    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        ButterKnife.bind(this);

        initToolbar();
        setViewProperties();
    }

    /**
     * Initialize Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Initialize view's listener
     */
    private void setViewProperties() {
        mButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new FilterDialogFragment();
                newFragment.show(getFragmentManager(), "missiles");
            }
        });
    }
}
