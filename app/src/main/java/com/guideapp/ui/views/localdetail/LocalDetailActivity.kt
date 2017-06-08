package com.guideapp.ui.views.localdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NavUtils
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

import com.guideapp.R
import com.guideapp.ui.views.BaseActivity
import com.guideapp.ui.views.local.LocalActivity
import com.guideapp.utilities.Utility

class LocalDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()
        setContentView(R.layout.activity_local_detail)
        setupToolbar()

        if (intent.hasExtra(EXTRA_LOCAL_ID)) {
            initFragment(LocalDetailFragment.newInstance(intent.getLongExtra(EXTRA_LOCAL_ID, 0)))
        }
    }

    private fun initFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, fragment)
        transaction.commit()
    }

    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val back = intent.getBooleanExtra(EXTRA_BACK, false)
        if (back) {
            super.onBackPressed()
        } else {
            navigateUpTo()
        }
    }

    private fun navigateUpTo() {
        val idCategory = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
        val upIntent = NavUtils.getParentActivityIntent(this)
        upIntent.putExtra(LocalActivity.EXTRA_CATEGORY, idCategory)
        upIntent.putExtra(LocalActivity.EXTRA_ID_TITLE, Utility.getIdDescriptionCategory(idCategory))
        NavUtils.navigateUpTo(this, upIntent)
    }

    companion object {
        val EXTRA_LOCAL_ID = "local_id"
        val EXTRA_CATEGORY_ID = "id_category"
        val EXTRA_BACK = "back"

        fun navigate(activity: Activity, transitionImage: View, id: Long, idCategory: Long) {
            val intent = Intent(activity, LocalDetailActivity::class.java)
            intent.putExtra(EXTRA_LOCAL_ID, id)
            intent.putExtra(EXTRA_CATEGORY_ID, idCategory)
            intent.putExtra(EXTRA_BACK, true)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, activity.getString(R.string.detail_icon_transition_name))
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }
}
