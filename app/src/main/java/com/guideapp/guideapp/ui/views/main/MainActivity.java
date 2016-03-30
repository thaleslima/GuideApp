package com.guideapp.guideapp.ui.views.main;

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
import com.guideapp.guideapp.ui.views.favorite.FavoriteFragment;
import com.guideapp.guideapp.ui.views.user.UserFragment;
import com.guideapp.guideapp.ui.views.whatshot.WhatsHotFragment;
import com.guideapp.guideapp.ui.views.BaseActivity;
import com.guideapp.guideapp.ui.views.search.SearchActivity;

/**
 * Main activity
 */
public class MainActivity extends BaseActivity {
    private static final int[] ICONS_TAB_BLACK = {
            R.drawable.ic_apps_black_24dp,
            R.drawable.ic_bookmark_black_24dp,
            R.drawable.ic_whatshot_black_24dp,
            R.drawable.ic_person_black_24dp
    };

    private static final int[] ICONS_TAB_WHITE = {
            R.drawable.ic_apps_white_24dp,
            R.drawable.ic_bookmark_white_24dp,
            R.drawable.ic_whatshot_white_24dp,
            R.drawable.ic_person_white_24dp
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
    }

    /**
     * Initialize view's listener
     */
    private void setViewProperties() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    /**
     * Initialize Toolbar
     */
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Initialize extras parameters
     */
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

    /**
     * Get Views by id
     */
    private void setFindViewById() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
    }

    /**
     * Sections of the view pager
     */
    private class SectionsAdapter extends FragmentPagerAdapter {
        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            switch (position) {
                case 0:
                    fragment = new MainFragment();
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
            return ICONS_TAB_BLACK.length;
        }
    }
}
