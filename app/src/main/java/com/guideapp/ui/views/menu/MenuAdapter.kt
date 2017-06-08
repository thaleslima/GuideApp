package com.guideapp.ui.views.menu

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.guideapp.R
import com.guideapp.model.MainMenu
import com.guideapp.utilities.inflate
import kotlinx.android.synthetic.main.item_menu.view.*

internal class MenuAdapter(private val mDataSet: List<MainMenu>,
                           private val mListener: MenuAdapter.RecyclerViewItemClickListener?) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    internal interface RecyclerViewItemClickListener {
        fun onItemClick(item: MainMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDataSet[position])
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    internal inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_menu)) {
        private var mItem: MainMenu? = null

        fun bind(item: MainMenu) = with(itemView) {
            mItem = item
            menu_item.setBackgroundResource(item.idColorPrimary)
            menu_title.setText(item.idTitle)
            menu_image.setImageResource(item.idImage)
            itemView.setOnClickListener { mItem?.let { it1 -> mListener?.onItemClick(it1) } }
        }
    }
}
