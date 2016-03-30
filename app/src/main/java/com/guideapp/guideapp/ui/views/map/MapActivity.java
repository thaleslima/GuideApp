package com.guideapp.guideapp.ui.views.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.BaseActivity;

/**
 * Map activity
 */
public class MapActivity extends BaseActivity {
    private long mIdCity;
    private long mIdCategory;
    private int mIdTitle;
    private long[] mIdSubCategories;

    private static final String EXTRA_CITY = "id-city";
    private static final String EXTRA_CATEGORY = "id-category";
    private static final String EXTRA_SUB_CATEGORY = "id-sub-category";
    private static final String EXTRA_ID_TITLE = "id-title";

    /**
     * Navigate to map activity
     * @param context The Context the view is running in
     */
    public static void navigate(Context context,
                                Long idCity) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(EXTRA_CITY, idCity);

        context.startActivity(intent);
    }


    /**
     * Navigate to map activity
     * @param context The Context the view is running in
     */
    public static void navigate(Context context,
                                Long idCity,
                                Long idCategory,
                                long[] idSubCategories,
                                int idTitle) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(EXTRA_CITY, idCity);
        intent.putExtra(EXTRA_CATEGORY, idCategory);
        intent.putExtra(EXTRA_SUB_CATEGORY, idSubCategories);
        intent.putExtra(EXTRA_ID_TITLE, idTitle);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initExtra();
        initToolbar();

        initFragment(MapFragment.newInstance(mIdCity, mIdCategory, mIdSubCategories));
    }

    /**
     * Initialize extras parameters
     */
    private void initExtra() {
        mIdCity = getIntent().getLongExtra(EXTRA_CITY, 0);
        mIdCategory = getIntent().getLongExtra(EXTRA_CATEGORY, 0);
        mIdTitle = getIntent().getIntExtra(EXTRA_ID_TITLE, 0);
        mIdSubCategories = getIntent().getLongArrayExtra(EXTRA_SUB_CATEGORY);
    }

    /**
     * Initialize fragment
     * @param fragment Fragment
     */
    private void initFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, fragment);
        transaction.commit();
    }

    /**
     * Initialize Toolbar
     */
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(mIdTitle > 0) {
            setTitle(mIdTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
}
