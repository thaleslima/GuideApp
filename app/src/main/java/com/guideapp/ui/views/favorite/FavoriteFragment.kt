package com.guideapp.ui.views.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.guideapp.R
import com.guideapp.model.Local
import com.guideapp.ui.views.localdetail.LocalDetailActivity
import com.guideapp.ui.views.DividerItemDecoration

class FavoriteFragment : Fragment(), FavoriteContract.View, FavoriteAdapter.ItemClickListener {
    private var mAdapter: FavoriteAdapter? = null
    private var mPresenter: FavoriteContract.Presenter? = null
    private var mMessageView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_favorite, container, false)
        setupRecyclerView(view)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        mMessageView = view.findViewById(R.id.message_view) as TextView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter = FavoritePresenter(this)
        mPresenter!!.loadLocals(activity.supportLoaderManager)
    }

    private fun setupRecyclerView(view: View) {
        mAdapter = FavoriteAdapter(getContext(), this)
        val recyclerView = view.findViewById(R.id.recycler) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(getContext())
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST))
    }

    override fun onResume() {
        super.onResume()

        mPresenter!!.onResume(activity.supportLoaderManager)
    }

    override fun showNoItemsMessage() {
        mMessageView!!.visibility = View.VISIBLE
    }

    override fun hideNoItemsMessage() {
        mMessageView!!.visibility = View.GONE
    }

    override fun onItemClick(item: Local, view: ImageView) {
        mPresenter!!.openLocalDetails(item, view)
    }

    override fun showLocals(locals: List<Local>) {
        mAdapter!!.replaceData(locals)
    }

    override fun showLocalDetailUi(local: Local, view: ImageView) {
        LocalDetailActivity.navigate(activity, view, local.id!!, local.idCategory)
    }
}
