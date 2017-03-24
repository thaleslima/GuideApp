package com.guideapp.guideapp.ui.views.localdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.BaseActivity;

public class LocalDetailActivity extends BaseActivity {
    public static final String EXTRA_LOCAL_ID = "local_id";

    public static void navigate(Activity activity, View transitionImage, long id) {
        Intent intent = new Intent(activity, LocalDetailActivity.class);
        intent.putExtra(EXTRA_LOCAL_ID, id);

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
        transaction.add(R.id.contentFrame, fragment);
        transaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
