package com.guideapp.guideapp.ui.views.search;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.search.SearchFragment.Callbacks;
import com.guideapp.guideapp.ui.views.BaseActivity;


/**
 * Created by thales on 7/30/15.
 */
public class SearchActivity extends BaseActivity implements Callbacks {
    private EditText mToolbarSearchView;
    private ImageView mSearchClearButton;
    private SearchFragment mSearchFragment;

    public static void navigate(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(activity, SearchActivity.class);
            ActivityOptions opts = ActivityOptions.makeSceneTransitionAnimation(activity);
            activity.startActivity(intent, opts.toBundle());
        } else {
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        setFindViewById();
        setViewProperties();
    }

    private void setViewProperties() {
        mToolbarSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString();
                if (search.isEmpty())
                    mSearchClearButton.setVisibility(View.GONE);
                else
                    mSearchClearButton.setVisibility(View.VISIBLE);

                if (mSearchFragment != null)
                    mSearchFragment.showSearchRecent(search);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mToolbarSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchFragment.showSearchRecent(mToolbarSearchView.getText().toString());
                showKeyboard();
            }
        });

        mToolbarSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchFragment.showResult(v.getText().toString());
                    return true;
                }

                return false;
            }
        });

        mSearchClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbarSearchView.setText("");
                showKeyboard();
            }
        });
    }

    private void setFindViewById() {
        mToolbarSearchView = (EditText) findViewById(R.id.search_view);
        mSearchClearButton = (ImageView) findViewById(R.id.search_clear);

        mSearchFragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentById(android.R.id.list);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setQuery(String query) {
        mToolbarSearchView.setText(query);
        hideKeyboard();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyboard() {
        InputMethodManager keyboard = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(mToolbarSearchView, 0);
    }
}
