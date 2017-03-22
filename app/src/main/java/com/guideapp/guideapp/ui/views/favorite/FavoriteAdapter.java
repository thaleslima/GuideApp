package com.guideapp.guideapp.ui.views.favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.LocalViewHolder> {
    private Context mContext;
    private FavoriteAdapter.ItemClickListener mListener;
    private List<Local> mDataSet;

    interface ItemClickListener {
        void onItemClick(Local item, ImageView view);
    }

    public FavoriteAdapter(Context context, FavoriteAdapter.ItemClickListener mListener) {
        this.mDataSet = new ArrayList<>();
        this.mContext = context;
        this.mListener = mListener;
    }

    @Override
    public FavoriteAdapter.LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local, parent, false);
        return new FavoriteAdapter.LocalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.LocalViewHolder holder, int position) {
        holder.populate(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    void replaceData(List<Local> dataSet) {
        setList(dataSet);
        notifyDataSetChanged();
    }

    private void setList(List<Local> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
    }

    class LocalViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView mPhotoView;
        private final TextView mDescriptionView;
        private final TextView mAddressView;
        private final TextView mDescriptionsSubCategory;
        private Local mItem;

        LocalViewHolder(View view) {
            super(view);
            mView = view;
            mPhotoView = (ImageView) view.findViewById(R.id.local_picture);
            mDescriptionView = (TextView) view.findViewById(R.id.local_text);
            mAddressView = (TextView) view.findViewById(R.id.local_address);
            mDescriptionsSubCategory = (TextView) view.findViewById(R.id.descriptions_sub_category);

            mView.setOnClickListener(v -> mListener.onItemClick(mItem, mPhotoView));
        }

        void populate(Local data) {
            mItem = data;
            mDescriptionView.setText(data.getDescription());
            mAddressView.setText(data.getAddress());
            mDescriptionsSubCategory.setText(data.getDescriptionSubCategories());
            Glide.with(mContext).load(data.getImagePath()).into(mPhotoView);
        }
    }
}
