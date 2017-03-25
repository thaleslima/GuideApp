package com.guideapp.guideapp.ui.views.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.ui.views.localdetail.LocalDetailActivity;
import com.guideapp.guideapp.utilities.Constants;
import com.guideapp.guideapp.utilities.Utility;
import com.guideapp.guideapp.utilities.ViewUtil;

import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment
        implements OnMapReadyCallback, MapContract.View {
    private static final String EXTRA_CITY = "id-city";
    private static final String EXTRA_CATEGORY = "id-category";
    private static final String EXTRA_SUB_CATEGORY = "id-sub-category";

    private SupportMapFragment mSupportMapFragment;
    private TextView mDescriptionSubCategoryView;
    private RelativeLayout mLocalView;
    private GoogleMap mMap;
    private MapContract.Presenter mPresenter;
    private TextView mDescriptionView;
    private ImageView mPhotoView;

    private HashMap<String, Local> mMarkersId;

    public static Fragment newInstance(long idCity, long idCategory, long[] idSubCategories) {
        Fragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CITY, idCity);
        bundle.putLong(EXTRA_CATEGORY, idCategory);
        bundle.putLongArray(EXTRA_SUB_CATEGORY, idSubCategories);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        setupViews(view);
        setupViewProperties();

        mMarkersId = new HashMap<>();
        mPresenter = new MapPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpMapIfNeeded();
    }

    private void setupViews(View view) {
        mLocalView = (RelativeLayout) view.findViewById(R.id.local_view);
        mDescriptionSubCategoryView = (TextView) mLocalView.findViewById(R.id.descriptions_sub_category);
        mDescriptionView = (TextView) mLocalView.findViewById(R.id.local_description);
        mPhotoView = (ImageView) mLocalView.findViewById(R.id.local_picture);
    }

    private void setupViewProperties() {
        mLocalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openLocalDetails((Local) v.getTag(), mPhotoView);
            }
        });
    }

    private void setUpMapIfNeeded() {
        if (mSupportMapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            mSupportMapFragment = ((SupportMapFragment) fm.findFragmentById(R.id.map));
            mSupportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng center = new LatLng(Constants.City.LATITUDE, Constants.City.LONGITUDE);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mPresenter.openLocalSummary(mMarkersId.get(marker.getId()));
                return false;
            }
        });

        mPresenter.loadLocals(getActivity().getSupportLoaderManager());
    }

    @Override
    public void showLocals(List<Local> locals) {
        LatLng latLng;
        Marker marker;

        mMarkersId.clear();

        for (Local local : locals) {
            latLng = new LatLng(local.getLatitude(), local.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(Utility.getIdImageCategory(local.getIdCategory())));

            marker = mMap.addMarker(markerOptions);

            mMarkersId.put(marker.getId(), local);
        }
    }

    @Override
    public void showLocalSummary(Local local) {
        mMap.setPadding(0, 0, 0, 250);

        mDescriptionView.setText(local.getDescription());
        mDescriptionSubCategoryView.setText(local.getDescriptionSubCategories());

        Glide.with(this.getContext()).load(local.getImagePath()).into(mPhotoView);

        ViewUtil.showViewLayout(getContext(), mLocalView);
        mLocalView.setTag(local);
    }

    @Override
    public void showLocalDetailUi(Local local, ImageView view) {
        LocalDetailActivity.navigate(this.getActivity(), view, local.getId(), local.getIdCategory());
    }

}
