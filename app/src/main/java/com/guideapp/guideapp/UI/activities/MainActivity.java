package com.guideapp.guideapp.ui.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.fragments.FavoriteFragment;
import com.guideapp.guideapp.ui.fragments.MainActivityFragment;
import com.guideapp.guideapp.ui.fragments.UserFragment;
import com.guideapp.guideapp.ui.fragments.WhatsHotFragment;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;

    private int[] mIconTabBlack = {
            R.drawable.ic_apps_black_24dp,
            R.drawable.ic_favorite_black_24dp,
            R.drawable.ic_whatshot_black_24dp,
            R.drawable.ic_person_black_24dp
    };

    private int[] mIconTabWhite = {
            R.drawable.ic_apps_white_24dp,
            R.drawable.ic_favorite_white_24dp,
            R.drawable.ic_whatshot_white_24dp,
            R.drawable.ic_person_white_24dp
    };

    private int mTotalTabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        setFindViewById();
        setViewProperties();
        setListener();
    }

    private void setListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
                tab.setIcon(mIconTabWhite[tab.getPosition()]);
                mAppBarLayout.setExpanded(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(mIconTabBlack[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setViewProperties() {
        for (int i = 0; i < mTotalTabs; i++) {
            if (i == 0)
                mTabLayout.addTab(mTabLayout.newTab().setIcon(mIconTabWhite[i]));
            else
                mTabLayout.addTab(mTabLayout.newTab().setIcon(mIconTabBlack[i]));
        }

        mViewPager.setAdapter(new SectionsAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setPageMargin(16);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {
            SearchActivity.navigate(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFindViewById() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
    }

    private class SectionsAdapter extends FragmentPagerAdapter {
        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            switch (position) {
                case 0:
                    fragment = new MainActivityFragment();
                    break;
                case 1:
                    fragment = new FavoriteFragment();
                    break;
                case 2:
                    fragment = new WhatsHotFragment();
                    break;
                default:
                    fragment = new UserFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
