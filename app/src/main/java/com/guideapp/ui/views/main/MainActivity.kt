package com.guideapp.ui.views.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.guideapp.R
import com.guideapp.data.local.GuideContract
import com.guideapp.sync.GuideSyncTask
import com.guideapp.sync.GuideSyncUtils
import com.guideapp.ui.views.BaseActivity
import com.guideapp.ui.views.favorite.FavoriteFragment
import com.guideapp.ui.views.menu.MenuFragment
import com.guideapp.utilities.Constants

class MainActivity : BaseActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    private var mTabLayout: TabLayout? = null
    private var mViewError: LinearLayout? = null
    private var mViewPager: ViewPager? = null
    private var mAppBarLayout: AppBarLayout? = null
    private var mProgressBar: ProgressBar? = null
    private var mBroadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupViews()
        setupViewPager()
        setupViewProperties()
        showProgressBar()

        supportLoaderManager.initLoader(ID_LOADER, Bundle.EMPTY, this)
        GuideSyncUtils.initialize(this)
    }

    private fun setupViewProperties() {
        mTabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager!!.setCurrentItem(tab.position, true)
                tab.setIcon(ICONS_TAB_BLACK[tab.position])
                mAppBarLayout!!.setExpanded(true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setIcon(ICONS_TAB_GREY[tab.position])
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setTitle(R.string.app_name_city)
        }
    }

    private fun setupViewPager() {
        for (i in ICONS_TAB_BLACK.indices) {
            if (i == 0) {
                mTabLayout!!.addTab(mTabLayout!!.newTab().setIcon(ICONS_TAB_BLACK[i]))
            } else {
                mTabLayout!!.addTab(mTabLayout!!.newTab().setIcon(ICONS_TAB_GREY[i]))
            }
        }

        mViewPager!!.adapter = SectionsAdapter(supportFragmentManager)
        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))
        mViewPager!!.pageMargin = 16
    }

    private fun setupViews() {
        mTabLayout = findViewById(R.id.tabLayout) as TabLayout
        mViewPager = findViewById(R.id.viewpager) as ViewPager
        mAppBarLayout = findViewById(R.id.appBarLayout) as AppBarLayout
        mViewError = findViewById(R.id.view_error) as LinearLayout
        mProgressBar = findViewById(R.id.progressBar) as ProgressBar
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor> {
        when (id) {
            ID_LOADER -> return CursorLoader(this, GuideContract.LocalEntry.CONTENT_URI, null, null, null, null)
            else -> throw RuntimeException("Loader Not Implemented: " + id)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        Log.d(TAG, "onLoadFinished: " + data.count)
        hideProgressBar()
        if (data.count == 0) {
            updateEmptyView()
        } else {
            showMenu()
        }
    }

    private fun updateEmptyView() {
        @GuideSyncTask.SyncStatus val status = GuideSyncTask.getSyncStatus(this)
        val message: Int
        when (status) {
            GuideSyncTask.LOCATION_STATUS_SERVER_DOWN -> message = R.string.empty_list_server_down
            GuideSyncTask.LOCATION_STATUS_NO_CONNECTION -> message = R.string.empty_list_no_network
            GuideSyncTask.LOCATION_STATUS_OK -> {
                registerReceiver()
                showProgressBar()
                return
            }
            else -> {
                registerReceiver()
                showProgressBar()
                return
            }
        }

        showErrorMessage(message)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver()
        checkProgress()
    }

    private fun checkProgress() {
        if (mProgressBar!!.visibility == View.VISIBLE) {
            updateEmptyView()
        }
    }

    private fun showErrorMessage(message: Int) {
        hideProgressBar()
        showErrorMessage()
        val messageView = findViewById(R.id.message_view) as TextView
        messageView.setText(message)
        findViewById(R.id.try_again).setOnClickListener {
            registerReceiver()
            hideErrorMessage()
            showProgressBar()
            GuideSyncUtils.startImmediateSync(this@MainActivity)
        }
    }

    private fun registerReceiver() {
        if (mBroadcastReceiver == null) {
            val intentFilter = IntentFilter(Constants.ACTION_DATA_SYNC_ERROR)
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
            mBroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    updateEmptyView()
                }
            }
            registerReceiver(mBroadcastReceiver, intentFilter)
        }
    }

    private fun unregisterReceiver() {
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver)
        }

        mBroadcastReceiver = null
    }

    private fun showErrorMessage() {
        mViewError!!.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        mViewError!!.visibility = View.GONE
    }

    private fun showMenu() {
        mViewPager!!.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        mProgressBar!!.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mProgressBar!!.visibility = View.GONE
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    private inner class SectionsAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val fragment: Fragment

            when (position) {
                0 -> fragment = MenuFragment()
                else -> fragment = FavoriteFragment()
            }

            return fragment
        }

        override fun getCount(): Int {
            return ICONS_TAB_BLACK.size
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val ID_LOADER = 456
        private val ICONS_TAB_BLACK = intArrayOf(R.drawable.ic_apps_black_24dp, R.drawable.ic_bookmark_black_24dp)
        private val ICONS_TAB_GREY = intArrayOf(R.drawable.ic_apps_grey_400_24dp, R.drawable.ic_bookmark_grey_400_24dp)
    }
}
