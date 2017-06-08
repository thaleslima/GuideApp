package com.guideapp.ui.views.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.guideapp.R
import com.guideapp.model.MainMenu

internal class MenuAdapter(private val mDataSet: List<MainMenu>, private val mListener: MenuAdapter.RecyclerViewItemClickListener?) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    internal interface RecyclerViewItemClickListener {
        fun onItemClick(item: MainMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populate(mDataSet[position])
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    internal inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val mContainer: View = mView.findViewById(R.id.menu_item)
        private val mImage: ImageView = mView.findViewById(R.id.menu_image) as ImageView
        private val mTitle: TextView = mView.findViewById(R.id.menu_title) as TextView
        private var mItem: MainMenu? = null

        init {
            mView.setOnClickListener {
                mItem?.let { it1 -> mListener?.onItemClick(it1) }
            }
        }

        fun populate(data: MainMenu) {
            mItem = data
            mContainer.setBackgroundResource(data.idColorPrimary)
            mTitle.setText(data.idTitle)
            mImage.setImageResource(data.idImage)
        }
    }
}
