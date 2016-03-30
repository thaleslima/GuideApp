package com.guideapp.guideapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenuTemp;
import com.guideapp.guideapp.ui.listener.RecyclerViewItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<MainMenuTemp> items;
    private RecyclerViewItemClickListener mListener;
    private Context mContext;

    public PhotoAdapter(Context context, List<MainMenuTemp> items) {
        this.items = items;
        this.mContext = context;
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_photo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainMenuTemp item = items.get(position);
        Picasso.with(mContext)
                .load(item.getText())
                .placeholder(R.color.black_light)
                .into(holder.image);

        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}
