package com.guideapp.guideapp.ui.adapters;

import android.app.DialogFragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.views.BaseActivity;
import com.guideapp.guideapp.ui.dialog.CommentDialogFragment;
import com.guideapp.guideapp.ui.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.model.LocalDetail;

import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalDetailAdapter extends RecyclerView.Adapter<LocalDetailAdapter.LocalViewHolder> {
    private FragmentActivity mContext;
    private RecyclerViewItemClickListener mListener;
    private List<LocalDetail> mDataSet;

    public static final int LOCAL_DETAIL = 1;
    public static final int LOCAL_DETAIL_MAP = 2;
    public static final int LOCAL_DETAIL_TITLE_OPINION = 3;
    public static final int LOCAL_DETAIL_OPINION = 4;

    public LocalDetailAdapter(FragmentActivity context, List<LocalDetail> dataSet) {
        mContext = context;
        this.mDataSet = dataSet;
    }


    public LocalDetailAdapter(BaseActivity context, RecyclerViewItemClickListener mListener,
                              List<LocalDetail> dataSet) {
        mContext = context;
        this.mListener = mListener;
        this.mDataSet = dataSet;
    }

    class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            OnMapReadyCallback {
        public final ImageView icoView;
        public final TextView textView;
        public final View dividerView;
        public final MapView map;
        public RatingBar ratingView;
        private LocalDetail mItem;

        public LocalViewHolder(View view) {
            super(view);
            icoView = (ImageView) view.findViewById(R.id.local_picture);
            textView = (TextView) view.findViewById(R.id.local_text);
            dividerView = view.findViewById(R.id.local_divider);
            map = (MapView) view.findViewById(R.id.mapImageView);
            ratingView = (RatingBar) view.findViewById(R.id.ratingBar);

            if (map != null) {
                map.onCreate(null);
                map.onResume();
            }

            view.setOnClickListener(this);
        }


        public void populate(LocalDetail data) {
            mItem = data;

            if (data.getViewType() == LOCAL_DETAIL) {
                icoView.setImageResource(data.getIco());
                textView.setText(data.getText());
                dividerView.setVisibility(data.isDivider() ? View.VISIBLE : View.GONE);
            }

            if (data.getViewType() == LOCAL_DETAIL_MAP && map != null) {
                map.getMapAsync(this);
            }

            if (data.getViewType() == LOCAL_DETAIL_OPINION) {
                LayerDrawable stars = (LayerDrawable) ratingView.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(
                        ContextCompat.getColor(mContext, R.color.primary_star),
                        PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(
                        ContextCompat.getColor(mContext, R.color.secondary_star),
                        PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(
                        ContextCompat.getColor(mContext, R.color.secondary_star),
                        PorterDuff.Mode.SRC_ATOP);

                ratingView.setRating(4.5f);
            }

            if (data.getViewType() == LOCAL_DETAIL_TITLE_OPINION) {
                LayerDrawable stars = (LayerDrawable) ratingView.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(
                        ContextCompat.getColor(mContext, R.color.primary_star),
                        PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(
                        ContextCompat.getColor(mContext, R.color.secondary_star),
                        PorterDuff.Mode.SRC_ATOP);

                ratingView.setRating(4.5f);

                ratingView.setOnRatingBarChangeListener((ratingBar, v, b) -> {
                    DialogFragment newFragment = new CommentDialogFragment();
                    newFragment.show(mContext.getFragmentManager(), "missiles");
                });
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng = new LatLng(mItem.getLatitude(), mItem.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            MarkerOptions mMarker = new MarkerOptions().position(
                    new LatLng(latLng.latitude, latLng.longitude))
                    .anchor(0.0f, 1.0f)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            googleMap.clear();
            googleMap.addMarker(mMarker);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getViewType();
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        int layout = 0;

        switch (viewType) {
            case LOCAL_DETAIL_MAP:
                layout = R.layout.item_local_map;
                break;
            case LOCAL_DETAIL:
                layout = R.layout.item_local_detail;
                break;
            case LOCAL_DETAIL_TITLE_OPINION:
                layout = R.layout.item_local_title_opinion;
                break;
            default:
                layout = R.layout.item_local_opinion;
                break;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(
                layout, parent, false);

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
     * @param dataSet A Local Detail list
     */
    public void replaceData(List<LocalDetail> dataSet) {
        setList(dataSet);
        notifyDataSetChanged();
    }

    /**
     * Set items in a dataSet
     * @param dataSet A Local Detail list
     */
    private void setList(List<LocalDetail> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
    }
}
