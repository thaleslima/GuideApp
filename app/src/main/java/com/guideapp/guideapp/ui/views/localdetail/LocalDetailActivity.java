package com.guideapp.guideapp.ui.views.localdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.BaseActivity;
import com.guideapp.guideapp.ui.views.local.LocalActivity;
import com.guideapp.guideapp.utilities.Utility;

public class LocalDetailActivity extends BaseActivity {
    public static final String EXTRA_LOCAL_ID = "local_id";
    public static final String EXTRA_CATEGORY_ID = "id_category";
    public static final String EXTRA_BACK = "back";

    public static void navigate(Activity activity, View transitionImage, long id, long idCategory) {
        Intent intent = new Intent(activity, LocalDetailActivity.class);
        intent.putExtra(EXTRA_LOCAL_ID, id);
        intent.putExtra(EXTRA_CATEGORY_ID, idCategory);
        intent.putExtra(EXTRA_BACK, true);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, transitionImage, activity.getString(R.string.detail_icon_transition_name));

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportPostponeEnterTransition();
        setContentView(R.layout.activity_local_detail);

        setupToolbar();

        if (getIntent().hasExtra(EXTRA_LOCAL_ID)) {
            initFragment(LocalDetailFragment.newInstance(getIntent().getLongExtra(EXTRA_LOCAL_ID, 0)));
        }
    }

    private void initFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        boolean back = getIntent().getBooleanExtra(EXTRA_BACK, false);

        if (back) {
            super.onBackPressed();
        } else {
            navigateUpTo();
        }
    }

    private void navigateUpTo() {
        long idCategory = getIntent().getLongExtra(EXTRA_CATEGORY_ID, 0);
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.putExtra(LocalActivity.EXTRA_CATEGORY, idCategory);
        upIntent.putExtra(LocalActivity.EXTRA_ID_TITLE, Utility.getIdDescriptionCategory(idCategory));

        NavUtils.navigateUpTo(this, upIntent);
    }
}
