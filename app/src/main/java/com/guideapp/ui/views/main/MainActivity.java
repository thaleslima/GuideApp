package com.guideapp.ui.views.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guideapp.R;
import com.guideapp.data.local.GuideContract;
import com.guideapp.sync.GuideSyncTask;
import com.guideapp.sync.GuideSyncUtils;
import com.guideapp.ui.views.BaseActivity;
import com.guideapp.ui.views.favorite.FavoriteFragment;
import com.guideapp.ui.views.menu.MenuFragment;
import com.guideapp.utilities.Constants;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ID_LOADER = 456;

    private static final int[] ICONS_TAB_BLACK = {
            R.drawable.ic_apps_black_24dp,
            R.drawable.ic_bookmark_black_24dp
    };

    private static final int[] ICONS_TAB_WHITE = {
            R.drawable.ic_apps_white_24dp,
            R.drawable.ic_bookmark_white_24dp
    };

    private TabLayout mTabLayout;
    private LinearLayout mViewError;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private ProgressBar mProgressBar;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupViews();
        setupViewPager();
        setupViewProperties();

        getSupportLoaderManager().initLoader(ID_LOADER, null, this);

        showProgressBar();
        GuideSyncUtils.initialize(this);
    }

    private void initAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setupViewProperties() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
                tab.setIcon(ICONS_TAB_WHITE[tab.getPosition()]);
                mAppBarLayout.setExpanded(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(ICONS_TAB_BLACK[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name_city);
        }
    }

    private void setupViewPager() {
        for (int i = 0; i < ICONS_TAB_BLACK.length; i++) {
            if (i == 0) {
                mTabLayout.addTab(mTabLayout.newTab().setIcon(ICONS_TAB_WHITE[i]));
            } else {
                mTabLayout.addTab(mTabLayout.newTab().setIcon(ICONS_TAB_BLACK[i]));
            }
        }

        mViewPager.setAdapter(new SectionsAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setPageMargin(16);
    }

    private void setupViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mViewError = (LinearLayout) findViewById(R.id.view_error);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_LOADER:
                return new CursorLoader(this,
                        GuideContract.LocalEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: " + data.getCount());

        hideProgressBar();

        if (data.getCount() == 0) {
            updateEmptyView();
        } else {
            showMenu();
            initAd();
        }
    }

    private void updateEmptyView() {
        @GuideSyncTask.SyncStatus int status = GuideSyncTask.getSyncStatus(this);

        int message = R.string.empty_list;
        switch (status) {
            case GuideSyncTask.LOCATION_STATUS_SERVER_DOWN:
                message = R.string.empty_list_server_down;
                break;

            case GuideSyncTask.LOCATION_STATUS_NO_CONNECTION:
                message = R.string.empty_list_no_network;
                break;

            case GuideSyncTask.LOCATION_STATUS_OK:
            default:
                showProgressBar();
                return;
        }

        showErrorMessage(message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
        checkProgress();
    }

    private void checkProgress() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            updateEmptyView();
        }
    }

    private void showErrorMessage(int message) {
        hideProgressBar();
        showErrorMessage();

        TextView messageView = (TextView) findViewById(R.id.message_view);
        messageView.setText(message);

        findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver();
                hideErrorMessage();
                showProgressBar();
                GuideSyncUtils.startImmediateSync(MainActivity.this);
            }
        });
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_DATA_SYNC_ERROR);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateEmptyView();
            }
        };

        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        mBroadcastReceiver = null;
    }

    private void showErrorMessage() {
        mViewError.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        mViewError.setVisibility(View.GONE);
    }

    private void showMenu() {
        mViewPager.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class SectionsAdapter extends FragmentPagerAdapter {
        SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            switch (position) {
                case 0:
                    fragment = new MenuFragment();
                    break;
                default:
                    fragment = new FavoriteFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return ICONS_TAB_BLACK.length;
        }
    }
}
