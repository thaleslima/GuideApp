package com.guideapp.guideapp.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.user.UserFragment;
import com.guideapp.guideapp.model.MenuUser;

import java.util.List;

public class UserVisitorAdapter extends RecyclerView.Adapter<UserVisitorAdapter.ViewHolder> {
    private static String TAG = UserVisitorAdapter.class.getName();
    private final List<MenuUser> mValues;
    private final UserFragment.OnListFragmentInteractionListener mListener;

    public UserVisitorAdapter(List<MenuUser> items, UserFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == MenuUser.TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.fragment_user_visitor_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.fragment_user_item, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Context context = holder.mView.getContext();

        if(holder.mDescriptionView != null){
            holder.mDescriptionView.setText
                    (context.getResources().getText(holder.mItem.getDescription()));
            holder.mDescriptionView.setCompoundDrawablesWithIntrinsicBounds
                    (ContextCompat.getDrawable(context, holder.mItem.getIcon()), null, null, null);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDescriptionView;

        public MenuUser mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDescriptionView = (TextView) view.findViewById(R.id.description_menu);
        }
    }
}
