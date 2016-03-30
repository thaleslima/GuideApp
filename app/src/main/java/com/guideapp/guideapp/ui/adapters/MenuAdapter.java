package com.guideapp.guideapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenu;
import java.util.List;

/**
 * Menu adapter
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<MainMenu> mDataSet;
    private RecyclerViewItemClickListener mListener;

    /**
     * Interface definition for a callback to be invoked when a recycler view is clicked.
     */
    public interface RecyclerViewItemClickListener {

        /**
         * Called when a view has been clicked.
         * @param item A MainMenuTemp object representing data's inputs.
         */
        void onItemClick(MainMenu item);
    }

    /**
     * Simple constructor to use when creating a view from code.
     * @param dataSet The dataSet
     * @param mListener The callback that will run
     */
    public MenuAdapter(List<MainMenu> dataSet, RecyclerViewItemClickListener mListener) {
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

    /**
     * Inner Class for a recycler view
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mContainer;
        private final ImageView mImage;
        private final TextView mTitle;
        private final View mView;
        private MainMenu mItem;


        /**
         * Simple constructor to use when creating a view from code.
         * @param view Recycle view item
         */
        public ViewHolder(View view) {
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

        /**
         * Populate the data in a recycle view item
         * @param data Menu object
         */
        public void populate(MainMenu data) {
            mItem = data;

            mContainer.setBackgroundResource(mItem.getIdColorPrimary());
            mTitle.setText(mItem.getIdTitle());
            mImage.setImageResource(mItem.getIdImage());
        }
    }
}
