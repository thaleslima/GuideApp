package com.guideapp.guideapp.ui.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
    private Context mContext;
    private RecyclerViewItemClickListener mListener;
    private List<Local> mDataSet;

    /**
     * Interface definition for a callback to be invoked when a recycler view is clicked.
     */
    public interface RecyclerViewItemClickListener {

        /**
         * Called when a view has been clicked.
         * @param item A Local object representing data's inputs.
         * @param view Transition image
         */
        void onItemClick(Local item, ImageView view);
    }

    /**
     * Simple constructor to use when creating a view from code.
     * @param context The Context the view is running in
     * @param mListener The callback that will run
     */
    public LocalAdapter(Context context, RecyclerViewItemClickListener mListener) {
        this.mDataSet = new ArrayList<>();
        this.mContext = context;
        this.mListener = mListener;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local,
                parent, false);
        return new LocalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        holder.populate(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * Set items in a dataSet and after update de recycler view
     * @param dataSet A Local list
     */
    public void replaceData(List<Local> dataSet) {
        setList(dataSet);
        notifyDataSetChanged();
    }

    /**
     * Set items in a dataSet
     * @param dataSet A Local list
     */
    private void setList(List<Local> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
    }


    /**
     * Inner Class for a recycler view
     */
    class LocalViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView mPhotoView;
        private final TextView mDescriptionView;
        private final TextView mAddressView;
        private final TextView mDescriptionsSubCategory;
        private final ImageView mFavoriteView;
        private final RatingBar mRatingView;
        private Local mItem;

        /**
         * Simple constructor to use when creating a view from code.
         * @param view Recycle view item
         */
        public LocalViewHolder(View view) {
            super(view);
            mView = view;
            mPhotoView = (ImageView) view.findViewById(R.id.local_picture);
            mDescriptionView = (TextView) view.findViewById(R.id.local_text);
            mAddressView = (TextView) view.findViewById(R.id.local_address);
            mFavoriteView = (ImageView) view.findViewById(R.id.local_favorite);
            mRatingView = (RatingBar) view.findViewById(R.id.ratingBar);
            mDescriptionsSubCategory =  (TextView) view.findViewById(R.id.descriptions_sub_category);
            mFavoriteView.setOnClickListener(this::showFilterPopup);

            mView.setOnClickListener(v -> mListener.onItemClick(mItem, mPhotoView));
        }

        /**
         * Show a popup in a particular view
         * @param v ViewFragment object
         */
        private void showFilterPopup(View v) {
            PopupMenu popup = new PopupMenu(mContext, v);
            popup.getMenuInflater().inflate(R.menu.menu_local, popup.getMenu());


            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_remove:
                        Toast.makeText(mContext, "action_add", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        }

        /**
         * Populate the data in a recycle view item
         * @param data Local object
         */
        public void populate(Local data) {
            mItem = data;
            mDescriptionView.setText(data.getDescription());
            mAddressView.setText(data.getAddress());
            mRatingView.setRating(4.3f);
            mDescriptionsSubCategory.setText(data.getDescriptionSubCategories());
            LayerDrawable stars = (LayerDrawable) mRatingView.getProgressDrawable();

            stars.getDrawable(2).setColorFilter(
                    ContextCompat.getColor(mContext, R.color.primary_star),
                    PorterDuff.Mode.SRC_ATOP);

            stars.getDrawable(1).setColorFilter(
                    ContextCompat.getColor(mContext, R.color.secondary_star),
                    PorterDuff.Mode.SRC_ATOP);

            stars.getDrawable(0).setColorFilter(
                    ContextCompat.getColor(mContext, R.color.secondary_star),
                    PorterDuff.Mode.SRC_ATOP);

            Glide.with(mContext)
                    .load(data.getImagePath())
                    .into(mPhotoView);
        }
    }
}
