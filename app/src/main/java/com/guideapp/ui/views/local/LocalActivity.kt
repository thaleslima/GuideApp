package com.guideapp.ui.views.local

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.ProgressBar

import com.guideapp.R
import com.guideapp.model.Local
import com.guideapp.ui.views.BaseActivity
import com.guideapp.ui.views.GridSpacingItemDecoration
import com.guideapp.ui.views.localdetail.LocalDetailActivity

class LocalActivity : BaseActivity(), LocalContract.View, LocalAdapter.ItemClickListener {
    private var mProgressBar: ProgressBar? = null
    private var mIdCategory: Long = 0
    private var mIdTitle: Int = 0
    private var mAdapter: LocalAdapter? = null
    private var mActionsListener: LocalContract.Presenter? = null

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (intent.hasExtra(EXTRA_CATEGORY) && intent.hasExtra(EXTRA_CATEGORY)) {
            setIntent(intent)
            initExtra()
            setupToolbar()

            mActionsListener!!.restartLoadLocals(supportLoaderManager, mIdCategory)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local)

        initExtra()
        setupViews()
        setupToolbar()
        setupRecyclerView()

        mActionsListener = LocalPresenter(this, mIdCategory)
        mActionsListener!!.loadLocals(supportLoaderManager)
    }

    private fun initExtra() {
        mIdCategory = intent.getLongExtra(EXTRA_CATEGORY, 0)
        mIdTitle = intent.getIntExtra(EXTRA_ID_TITLE, 0)
    }

    private fun setupViews() {
        mProgressBar = findViewById(R.id.progressBar) as ProgressBar
    }

    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setTitle(mIdTitle)
        }
    }

    private fun setupRecyclerView() {
        mAdapter = LocalAdapter(this, this)
        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(GridSpacingItemDecoration(1, 16, true))
    }

    override fun showLocals(locals: List<Local>) {
        mAdapter!!.replaceData(locals)
    }

    override fun showLocalDetailUi(local: Local, view: ImageView) {
        LocalDetailActivity.navigate(this, view, local.id!!, local.idCategory)
    }

    override fun showProgressBar() {
        mProgressBar!!.visibility = android.view.View.VISIBLE
    }

    override fun hideProgressBar() {
        mProgressBar!!.visibility = android.view.View.GONE
    }

    override fun getContext(): Context {
        return this
    }

    override fun onItemClick(item: Local, view: ImageView) {
        mActionsListener!!.openLocalDetails(item, view)
    }

    companion object {
        val EXTRA_CATEGORY = "id-category"
        val EXTRA_ID_TITLE = "id-title"

        fun navigate(context: Context, idCategory: Long?, idTitle: Int) {
            val intent = Intent(context, LocalActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY, idCategory)
            intent.putExtra(EXTRA_ID_TITLE, idTitle)

            context.startActivity(intent)
        }
    }
}
