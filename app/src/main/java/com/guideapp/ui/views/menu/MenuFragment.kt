package com.guideapp.ui.views.menu


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.guideapp.R
import com.guideapp.model.MainMenu
import com.guideapp.ui.views.GridSpacingItemDecoration
import com.guideapp.ui.views.local.LocalActivity
import com.guideapp.ui.views.map.MapActivity
import com.guideapp.utilities.Constants
import com.guideapp.utilities.Utility
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment(), MenuAdapter.RecyclerViewItemClickListener {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler.layoutManager = GridLayoutManager(this.activity, 2)
        val adapter = MenuAdapter(Utility.menus, this)
        recycler.adapter = adapter
        recycler.addItemDecoration(GridSpacingItemDecoration(2, 16, true))
    }

    override fun onItemClick(item: MainMenu) {
        if (item.id == 1L) {
            MapActivity.navigate(this.activity, Constants.City.ID)
            return
        }
        LocalActivity.navigate(this.activity, item.id, item.idTitle)
    }
}
