package com.guideapp.guideapp.ui.views.map;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.ui.infrastructure.CommonUtils;

import java.util.HashMap;

/**
 * Created by thales on 7/28/15.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment mSupportMapFragment;
    private RatingBar mRatingBar;
    private RelativeLayout mLocalView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        setFindViewById(view);
        setViewProperties();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpMapIfNeeded();
    }

    private void setFindViewById(View view) {
        mLocalView = (RelativeLayout) view.findViewById(R.id.local_view);
        mRatingBar = (RatingBar) mLocalView.findViewById(R.id.ratingBar);
    }

    private void setViewProperties() {
        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.primary_star),
                PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.secondary_star),
                PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.secondary_star),
                PorterDuff.Mode.SRC_ATOP);

        mRatingBar.setRating(4.5f);
    }

    private void setUpMapIfNeeded() {
        if (mSupportMapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            mSupportMapFragment = ((SupportMapFragment) fm.findFragmentById(R.id.map));
            mSupportMapFragment.getMapAsync(this);

        }
    }

    private HashMap<String, Integer> mMarkersId;


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng sydney = new LatLng(-20.3449802, -46.8551188);
        LatLng sydney2 = new LatLng(-20.448558, -46.8564977);

        mMarkersId = new HashMap<>();

        Marker marker1 = map.addMarker(new MarkerOptions().position(sydney));
        Marker marke2 = map.addMarker(new MarkerOptions().position(sydney2));

        mMarkersId.put(marker1.getId(), 0);
        mMarkersId.put(marke2.getId(), 1);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.setPadding(0, 0, 0, 250);
                CommonUtils.showViewLayout(getContext(), mLocalView);

                return false;
            }
        });

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }
}
