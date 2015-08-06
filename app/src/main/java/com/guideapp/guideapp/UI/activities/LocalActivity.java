package com.guideapp.guideapp.UI.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.UI.adapters.LocalAdapter;
import com.guideapp.guideapp.UI.listener.RecyclerViewItemClickListener;
import com.guideapp.guideapp.UI.widget.DividerItemDecoration;
import com.guideapp.guideapp.model.Local;
import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends BaseActivity implements RecyclerViewItemClickListener, OnMapReadyCallback {
    private RecyclerView mRecyclerView;
    private LocalAdapter mAdapter;
    private List<Local> mDataSet;
    private SupportMapFragment mMapFragment;

    public static void navigate(Context context) {
        Intent intent = new Intent(context, LocalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFindViewById();
        setViewProperties();
    }

    private void setFindViewById() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    private void setViewProperties() {
        mDataSet = new ArrayList<>();
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));
        mDataSet.add(new Local("Cachoeira da gruta", "Complexo do claro"));

        mAdapter = new LocalAdapter(this, this, mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 310));
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        LocalDetailActivity.navigate(this, view.findViewById(R.id.local_ico));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-20.3489013, -46.8477856);
        LatLng sydney2 = new LatLng(-20.3486249, -46.8453948);

        IconGenerator iconFactory = new IconGenerator(this);

        iconFactory.setColor(getResources().getColor(R.color.primary_red));
        iconFactory.setTextAppearance(this, com.google.maps.android.R.style.Bubble_TextAppearance_Light);

        googleMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("1"))).
                position(sydney).title("Marker in Sydney").
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));

        googleMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("2"))).
                position(sydney2).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }
}
