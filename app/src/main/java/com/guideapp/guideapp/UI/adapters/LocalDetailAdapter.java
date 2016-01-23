package com.guideapp.guideapp.UI.adapters;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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
import com.guideapp.guideapp.UI.activities.BaseActivity;
import com.guideapp.guideapp.UI.fragments.CommentDialogFragment;
import com.guideapp.guideapp.UI.fragments.FilterDialogFragment;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.model.LocalDetail;

import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class LocalDetailAdapter extends RecyclerView.Adapter<LocalDetailAdapter.LocalViewHolder> {
    private BaseActivity mContext;
    private RecyclerViewItemClickListener mListener;
    private List<LocalDetail> mDataSet;

    public static final int LOCAL_DETAIL = 1;
    public static final int LOCAL_DETAIL_MAP = 2;
    public static final int LOCAL_DETAIL_TITLE_OPINION = 3;
    public static final int LOCAL_DETAIL_OPINION = 4;

    public LocalDetailAdapter(BaseActivity context, RecyclerViewItemClickListener mListener) {
        mContext = context;
        this.mListener = mListener;
    }

    public LocalDetailAdapter(BaseActivity context, RecyclerViewItemClickListener mListener, List<LocalDetail> dataSet) {
        mContext = context;
        this.mListener = mListener;
        this.mDataSet = dataSet;
    }

    class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnMapReadyCallback {
        public final ImageView icoView;
        public final TextView textView;
        public final View dividerView;
        public final MapView map;
        public RatingBar ratingView;

        public LocalViewHolder(View view) {
            super(view);
            icoView = (ImageView) view.findViewById(R.id.local_picture);
            textView = (TextView) view.findViewById(R.id.local_text);
            dividerView = view.findViewById(R.id.local_divider);
            map = (MapView) view.findViewById(R.id.mapImageView);
            ratingView = (RatingBar) view.findViewById(R.id.ratingBar);

            if (map != null)
            {
                map.onCreate(null);
                map.onResume();
            }

            view.setOnClickListener(this);
        }


        public void populate(LocalDetail data) {
            if(data.getViewType() == LOCAL_DETAIL)
            {
                icoView.setImageResource(data.getIco());
                textView.setText(data.getText());
                dividerView.setVisibility(data.isDivider() ? View.VISIBLE : View.GONE);
            }

            if(data.getViewType() == LOCAL_DETAIL_MAP && map != null)
            {
                map.getMapAsync(this);
            }

            if(data.getViewType() == LOCAL_DETAIL_OPINION)
            {
                LayerDrawable stars = (LayerDrawable) ratingView.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.primary_star), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.secondary_star), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.secondary_star), PorterDuff.Mode.SRC_ATOP);

                ratingView.setRating(4.5f);
            }

            if(data.getViewType() == LOCAL_DETAIL_TITLE_OPINION)
            {
                LayerDrawable stars = (LayerDrawable) ratingView.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.primary_star), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.secondary_star), PorterDuff.Mode.SRC_ATOP);
                //stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.secondary_star), PorterDuff.Mode.SRC_ATOP);

                ratingView.setRating(4.5f);

                ratingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                        DialogFragment newFragment = new CommentDialogFragment();
                        newFragment.show(mContext.getFragmentManager(), "missiles");
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng = new LatLng(-22.8161511, -47.0455066);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            MarkerOptions mMarker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                    .title("Modelo: " + "AAA-9090"
                            + "\n Cor: " + "Preto")
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
        View v = null;

        switch (viewType)
        {
            case LOCAL_DETAIL_MAP:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_map, parent, false);
                break;
            case LOCAL_DETAIL:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_detail, parent, false);
                break;
            case LOCAL_DETAIL_TITLE_OPINION:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_title_opinion, parent, false);
                break;
            case LOCAL_DETAIL_OPINION:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_opinion, parent, false);
                break;
        }

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
}
