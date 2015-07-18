package com.guideapp.guideapp.UI.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.fragments.LocalFragment;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFindViewById();
        setViewProperties();
        setListener();

        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
    }

    private void setListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setViewProperties() {
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_terrain_white_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_restaurant_menu_white_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_local_hotel_white_24dp));
        mViewPager.setAdapter(new SectionsAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        setSupportActionBar(mToolbar);
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFindViewById() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    private class SectionsAdapter extends FragmentPagerAdapter {
        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new LocalFragment();
                    break;
                case 1:
                    fragment = new LocalFragment();
                    break;
                case 2:
                    fragment = new LocalFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
