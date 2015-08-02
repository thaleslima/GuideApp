package com.guideapp.guideapp.UI.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.fragments.LocalFragment;
import com.guideapp.guideapp.UI.fragments.MainActivityFragment;
import com.guideapp.guideapp.UI.fragments.UserFragment;
import com.guideapp.guideapp.UI.infrastructure.CommonUtils;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFabView;

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

                if(tab.getPosition() == 2)
                {
                    CommonUtils.dismissViewLayout(MainActivity.this, mFabView);
                }
                else
                {
                    CommonUtils.showViewLayout(MainActivity.this, mFabView);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void setViewProperties() {
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_menu_white_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_favorite_white_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_person_white_24dp));
        mViewPager.setAdapter(new SectionsAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mFabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.navigate(MainActivity.this);
            }
        });
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
        mFabView = (FloatingActionButton) findViewById(R.id.fab);
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
                    fragment = new MainActivityFragment();
                    break;
                case 1:
                    fragment = new LocalFragment();
                    break;
                case 2:
                    fragment = new UserFragment();
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
