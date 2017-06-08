package com.guideapp.ui.views.menu


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
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

class MenuFragment : Fragment(), MenuAdapter.RecyclerViewItemClickListener {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_menu, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById(R.id.recycler) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this.activity, 2)
        val adapter = MenuAdapter(Utility.menus, this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 16, true))
    }

    override fun onItemClick(item: MainMenu) {
        if (item.id == 1L) {
            MapActivity.navigate(this.activity, Constants.City.ID)
            return
        }
        LocalActivity.navigate(this.activity, item.id, item.idTitle)
    }
}
