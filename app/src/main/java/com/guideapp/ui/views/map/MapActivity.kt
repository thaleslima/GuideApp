package com.guideapp.ui.views.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar

import com.guideapp.R
import com.guideapp.ui.views.BaseActivity

class MapActivity : BaseActivity() {
    private var mIdCity: Long = 0
    private var mIdCategory: Long = 0
    private var mIdTitle: Int = 0
    private var mIdSubCategories: LongArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initExtra()
        setupToolbar()
        setupFragment(MapFragment.newInstance(mIdCity, mIdCategory, mIdSubCategories))
    }

    private fun initExtra() {
        mIdCity = intent.getLongExtra(EXTRA_CITY, 0)
        mIdCategory = intent.getLongExtra(EXTRA_CATEGORY, 0)
        mIdTitle = intent.getIntExtra(EXTRA_ID_TITLE, 0)
        mIdSubCategories = intent.getLongArrayExtra(EXTRA_SUB_CATEGORY)
    }

    private fun setupFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.contentFrame, fragment)
        transaction.commit()
    }

    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setTitle(R.string.app_name_city)
        }

        if (mIdTitle > 0) {
            setTitle(mIdTitle)
        }
    }

    companion object {
        private val EXTRA_CITY = "id-city"
        private val EXTRA_CATEGORY = "id-category"
        private val EXTRA_SUB_CATEGORY = "id-sub-category"
        private val EXTRA_ID_TITLE = "id-title"

        fun navigate(context: Context, idCity: Long?) {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra(EXTRA_CITY, idCity)
            context.startActivity(intent)
        }
    }
}
