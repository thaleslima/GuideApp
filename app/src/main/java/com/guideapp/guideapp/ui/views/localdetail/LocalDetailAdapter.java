package com.guideapp.guideapp.ui.views.localdetail;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.LocalDetail;

import java.util.ArrayList;
import java.util.List;

class LocalDetailAdapter extends RecyclerView.Adapter<LocalDetailAdapter.LocalViewHolder> {
    private final List<LocalDetail> mDataSet = new ArrayList<>();

    static final int LOCAL_DETAIL = 1;
    static final int LOCAL_PHONE = 2;
    static final int LOCAL_SITE = 3;
    static final int LOCAL_DETAIL_MAP = 4;

    @NonNull
    private final ClickListener mClickListener;

    interface ClickListener {
        void dialPhoneNumber(String number);

        void openPage(String url);
    }

    LocalDetailAdapter(@NonNull ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    class LocalViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        private final ImageView icoView;
        private final TextView textView;
        private final View dividerView;
        private final MapView map;
        private LocalDetail mItem;
        private View mView;

        LocalViewHolder(View view) {
            super(view);
            mView = view;

            icoView = (ImageView) view.findViewById(R.id.local_picture);
            textView = (TextView) view.findViewById(R.id.local_text);
            dividerView = view.findViewById(R.id.local_divider);
            map = (MapView) view.findViewById(R.id.mapImageView);

            if (map != null) {
                map.onCreate(null);
                map.onResume();
            }
        }


        void populate(LocalDetail data) {
            mItem = data;

            if (data.getViewType() == LOCAL_DETAIL_MAP && map != null) {
                map.getMapAsync(this);
            } else {
                mView.setOnClickListener(null);
                mView.setClickable(false);
                textView.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.primary_text));
                icoView.setImageResource(data.getIco());
                textView.setText(data.getText());
                dividerView.setVisibility(data.isDivider() ? View.VISIBLE : View.GONE);
            }

            if (data.getViewType() == LOCAL_PHONE) {
                mView.setTag(data.getText());
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.dialPhoneNumber((String) v.getTag());
                    }
                });

            } else if (data.getViewType() == LOCAL_SITE) {
                textView.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.accent));
                mView.setTag(data.getText());
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.openPage((String) v.getTag());
                    }
                });
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng = new LatLng(mItem.getLatitude(), mItem.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(mItem.getIdImageMarker()));

//            MarkerOptions mMarker = new MarkerOptions().position(
//                    new LatLng(latLng.latitude, latLng.longitude)).anchor(0.0f, 1.0f)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            googleMap.clear();
            googleMap.addMarker(markerOptions);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getViewType();
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        int layout;

        switch (viewType) {
            case LOCAL_DETAIL_MAP:
                layout = R.layout.item_local_map;
                break;
            default:
                layout = R.layout.item_local_detail;
                break;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
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

    void replaceData(List<LocalDetail> dataSet) {
        setList(dataSet);
        notifyDataSetChanged();
    }

    private void setList(List<LocalDetail> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
    }
}
