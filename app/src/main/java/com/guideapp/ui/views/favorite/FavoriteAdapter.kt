package com.guideapp.ui.views.favorite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.guideapp.R
import com.guideapp.model.Local

import java.util.ArrayList

internal class FavoriteAdapter(private val mContext: Context, private val mListener: FavoriteAdapter.ItemClickListener) : RecyclerView.Adapter<FavoriteAdapter.LocalViewHolder>() {
    private val mDataSet: MutableList<Local>

    internal interface ItemClickListener {
        fun onItemClick(item: Local, view: ImageView)
    }

    init {
        this.mDataSet = ArrayList<Local>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.LocalViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_local_favorite, parent, false)
        return LocalViewHolder(v)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.LocalViewHolder, position: Int) {
        holder.populate(mDataSet[position])
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

    internal inner class LocalViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        private val mPhotoView: ImageView = mView.findViewById(R.id.local_picture) as ImageView
        private val mDescriptionView: TextView = mView.findViewById(R.id.local_text) as TextView
        private val mAddressView: TextView = mView.findViewById(R.id.local_address) as TextView
        private val mDescriptionsSubCategory: TextView = mView.findViewById(R.id.descriptions_sub_category) as TextView
        private var mItem: Local? = null

        init {
            mView.setOnClickListener { mListener.onItemClick(mItem!!, mPhotoView) }
        }

        fun populate(data: Local) {
            mItem = data
            mDescriptionView.text = data.description
            mAddressView.text = data.address
            mDescriptionsSubCategory.text = data.descriptionSubCategories

            Glide.with(mContext)
                    .load(data.imagePath)
                    .placeholder(R.color.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mPhotoView)
        }
    }
}
