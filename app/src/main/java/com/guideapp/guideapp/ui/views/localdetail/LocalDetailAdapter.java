package com.guideapp.guideapp.ui.views.localdetail;

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
    static final int LOCAL_DETAIL_MAP = 2;

    LocalDetailAdapter() {
    }

    class LocalViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        final ImageView icoView;
        final TextView textView;
        final View dividerView;
        final MapView map;
        LocalDetail mItem;

        LocalViewHolder(View view) {
            super(view);
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

            if (data.getViewType() == LOCAL_DETAIL) {
                icoView.setImageResource(data.getIco());
                textView.setText(data.getText());
                dividerView.setVisibility(data.isDivider() ? View.VISIBLE : View.GONE);
            }

            if (data.getViewType() == LOCAL_DETAIL_MAP && map != null) {
                map.getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng = new LatLng(mItem.getLatitude(), mItem.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            MarkerOptions mMarker = new MarkerOptions().position(
                    new LatLng(latLng.latitude, latLng.longitude)).anchor(0.0f, 1.0f)
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
