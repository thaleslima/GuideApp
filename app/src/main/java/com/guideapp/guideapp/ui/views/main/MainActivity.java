package com.guideapp.guideapp.ui.views.main;

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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.data.local.GuideContract;
import com.guideapp.guideapp.sync.GuideSyncUtils;
import com.guideapp.guideapp.ui.views.BaseActivity;
import com.guideapp.guideapp.ui.views.favorite.FavoriteFragment;
import com.guideapp.guideapp.ui.views.menu.MenuFragment;

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
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        setFindViewById();
        initViewPager();
        setViewProperties();
        initAd();

        getSupportLoaderManager().initLoader(ID_LOADER, null, this);
        GuideSyncUtils.initialize(this);
    }

    private void initAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setViewProperties() {
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

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name_city);
        }
    }

    private void initViewPager() {
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

    private void setFindViewById() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
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
