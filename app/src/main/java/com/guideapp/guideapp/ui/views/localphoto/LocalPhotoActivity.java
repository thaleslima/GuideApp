package com.guideapp.guideapp.ui.views.localphoto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.BaseActivity;


/**
 * Created by thales on 7/30/15.
 */
public class LocalPhotoActivity extends BaseActivity {
    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalPhotoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_photo);

        initToolbar();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
