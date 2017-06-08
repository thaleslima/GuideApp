package com.guideapp.ui.views.local

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.guideapp.R
import com.guideapp.model.Local
import com.guideapp.utilities.inflate
import kotlinx.android.synthetic.main.item_local.view.*
import java.util.*

internal class LocalAdapter(private val mListener: LocalAdapter.ItemClickListener) : RecyclerView.Adapter<LocalAdapter.LocalViewHolder>() {
    private val mDataSet: MutableList<Local> = ArrayList()

    internal interface ItemClickListener {
        fun onItemClick(item: Local, view: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        return LocalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        holder.bind(mDataSet[position])
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    fun replaceData(dataSet: List<Local>) {
        setList(dataSet)
        notifyDataSetChanged()
    }

    private fun setList(dataSet: List<Local>) {
        mDataSet.clear()
        mDataSet.addAll(dataSet)
    }

    internal inner class LocalViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_local)) {
        private var mItem: Local? = null

        fun bind(item: Local) = with(itemView) {
            mItem = item
            local_text.text = item.description
            local_address.text = item.address
            descriptions_sub_category.text = item.descriptionSubCategories

            Glide.with(itemView.context)
                    .load(item.imagePath)
                    .placeholder(R.color.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(local_picture)

            itemView.setOnClickListener { mItem?.let { it1 -> mListener.onItemClick(it1, local_picture) } }
        }
    }
}
