package com.guideapp.ui.views.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.util.Log
import android.view.View
import android.widget.TextView
import com.guideapp.R
import com.guideapp.data.local.GuideContract
import com.guideapp.sync.GuideSyncTask
import com.guideapp.sync.GuideSyncUtils
import com.guideapp.ui.views.BaseActivity
import com.guideapp.ui.views.favorite.FavoriteFragment
import com.guideapp.ui.views.menu.MenuFragment
import com.guideapp.utilities.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    private var mBroadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupViewPager()
        setupViewProperties()
        showProgressBar()

        supportLoaderManager.initLoader(ID_LOADER, Bundle.EMPTY, this)
        GuideSyncUtils.initialize(this)
    }

    private fun setupViewProperties() {
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager?.setCurrentItem(tab.position, true)
                tab.setIcon(ICONS_TAB_BLACK[tab.position])
                appBarLayout?.setExpanded(true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setIcon(ICONS_TAB_GREY[tab.position])
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name_city)
    }

    private fun setupViewPager() {
        for (i in ICONS_TAB_BLACK.indices) {
            if (i == 0) {
                tabLayout?.newTab()?.setIcon(ICONS_TAB_BLACK[i])?.let { tabLayout?.addTab(it) }
            } else {
                tabLayout?.newTab()?.setIcon(ICONS_TAB_GREY[i])?.let { tabLayout?.addTab(it) }
            }
        }

        viewpager?.let { viewPager ->
            viewPager.adapter = SectionsAdapter(supportFragmentManager)
            viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            viewPager.pageMargin = 16
        }
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
        if (progressBar?.visibility == View.VISIBLE) {
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
        view_error?.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        view_error?.visibility = View.GONE
    }

    private fun showMenu() {
        viewpager?.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.GONE
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
