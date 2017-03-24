package com.guideapp.guideapp.ui.views.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenu;

import java.util.List;

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private final List<MainMenu> mDataSet;
    private final RecyclerViewItemClickListener mListener;

    interface RecyclerViewItemClickListener {
        void onItemClick(MainMenu item);
    }

    MenuAdapter(List<MainMenu> dataSet, RecyclerViewItemClickListener mListener) {
        this.mDataSet = dataSet;
        this.mListener = mListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mContainer;
        private final ImageView mImage;
        private final TextView mTitle;
        private final View mView;
        private MainMenu mItem;

        ViewHolder(View view) {
            super(view);

            mView = view;
            mContainer = view.findViewById(R.id.menu_item);
            mImage = (ImageView) view.findViewById(R.id.menu_image);
            mTitle = (TextView) view.findViewById(R.id.menu_title);

            mView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(mItem);
                }
            });
        }

        void populate(MainMenu data) {
            mItem = data;

            mContainer.setBackgroundResource(mItem.getIdColorPrimary());
            mTitle.setText(mItem.getIdTitle());
            mImage.setImageResource(mItem.getIdImage());
        }
    }
}
