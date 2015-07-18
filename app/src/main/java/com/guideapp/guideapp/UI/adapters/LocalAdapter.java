package com.guideapp.guideapp.UI.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.model.Local;

import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
    private Context mContext;
    private Cursor data;
    private RecyclerViewItemClickListener mListener;
    private List<Local> mDataSet;

    public LocalAdapter(Context context, RecyclerViewItemClickListener mListener) {
        mContext = context;
        this.mListener = mListener;
    }

    public LocalAdapter(Context context, RecyclerViewItemClickListener mListener, List<Local> dataSet) {
        mContext = context;
        this.mListener = mListener;
        this.mDataSet = dataSet;
    }

    class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView photoView;
        public final TextView descriptionView;
        public final TextView addressView;


        public LocalViewHolder(View view) {
            super(view);
            photoView = (ImageView) view.findViewById(R.id.local_photo);
            descriptionView = (TextView) view.findViewById(R.id.local_description);
            addressView = (TextView) view.findViewById(R.id.local_address);
            view.setOnClickListener(this);
        }

        public void populate(Cursor data) {
            String description = data.getString(0);
            String photo = data.getString(1);
            String address = data.getString(2);

            descriptionView.setText(description);
            addressView.setText(address);

            Glide.with(mContext.getApplicationContext())
                    .load(photo)
                    .into(photoView);
        }

        public void populate(Local data) {
            descriptionView.setText(data.getDescription());
            addressView.setText(data.getAddress());
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view, getLayoutPosition());
            }
        }
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local, parent, false);
        return new LocalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        holder.populate(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.getCount();
        }
        return mDataSet.size();
    }

    public void swapCursor(Cursor data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Cursor getCursor()
    {
        return this.data;
    }
}
