package com.guideapp.guideapp.ui.views.map;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.ui.infrastructure.CommonUtils;
import com.guideapp.guideapp.ui.views.local.LocalContract;
import com.guideapp.guideapp.ui.views.local.LocalFragmentPresenter;
import com.guideapp.guideapp.ui.views.localdetail.LocalDetailActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thales on 7/28/15.
 */
public class MapFragment
        extends Fragment
        implements OnMapReadyCallback, MapContract.View {

    private SupportMapFragment mSupportMapFragment;
    private RatingBar mRatingBar;
    private RelativeLayout mLocalView;
    private GoogleMap mMap;
    private MapContract.UserActionsListener mActionsListener;

    private static final String EXTRA_CITY = "id-city";
    private static final String EXTRA_CATEGORY = "id-category";
    private static final String EXTRA_SUB_CATEGORY = "id-sub-category";

    private long mIdCity;
    private long mIdCategory;
    private long[] mIdSubCategories;
    private HashMap<String, Local> mMarkersId;

    /**
     * Create new instance
     * @param idCity Id city
     * @param idCategory Id Category
     * @param idSubCategories Id Sub category
     * @return Return LocalFragment instance
     */
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

        setFindViewById(view);
        setViewProperties();

        initExtra();

        mMarkersId = new HashMap<>();
        mActionsListener = new MapPresenter(this, getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpMapIfNeeded();
    }

    /**
     * Initialize extras parameters
     */
    private void initExtra() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            mIdCity = bundle.getLong(EXTRA_CITY, 0);
            mIdCategory = bundle.getLong(EXTRA_CATEGORY, 0);
            mIdSubCategories = bundle.getLongArray(EXTRA_SUB_CATEGORY);
        }
    }

    private TextView mDescriptionView;
    private ImageView mPhotoView;

    private void setFindViewById(View view) {
        mLocalView = (RelativeLayout) view.findViewById(R.id.local_view);
        mRatingBar = (RatingBar) mLocalView.findViewById(R.id.ratingBar);
        mDescriptionView = (TextView) mLocalView.findViewById(R.id.local_description);
        mPhotoView = (ImageView) mLocalView.findViewById(R.id.local_picture);
    }

    private void setViewProperties() {
        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(
                ContextCompat.getColor(this.getContext(), R.color.primary_star),
                PorterDuff.Mode.SRC_ATOP);

        stars.getDrawable(1).setColorFilter(
                ContextCompat.getColor(this.getContext(), R.color.secondary_star),
                PorterDuff.Mode.SRC_ATOP);

        stars.getDrawable(0).setColorFilter(
                ContextCompat.getColor(this.getContext(), R.color.secondary_star),
                PorterDuff.Mode.SRC_ATOP);

        mLocalView.setOnClickListener(v -> mActionsListener.loadLocal((Local) v.getTag()));
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
        LatLng center = new LatLng(-20.3449802, -46.8551188);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15));

        map.setOnMarkerClickListener(marker -> {
            mActionsListener.loadLocalSummary(mMarkersId.get(marker.getId()));

            return false;
        });

        mActionsListener.loadLocals(mIdCity, mIdCategory, mIdSubCategories);
    }

    @Override
    public void showLocals(List<Local> locals) {
        int size = locals.size();
        LatLng latLng;
        Marker marker;

        mMarkersId.clear();

        for (int i = 0; i < size; i++) {
            latLng = new LatLng(locals.get(i).getLatitude(), locals.get(i).getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(latLng));
            mMarkersId.put(marker.getId(), locals.get(i));
        }
    }

    @Override
    public void showLocalSummary(Local local) {
        mMap.setPadding(0, 0, 0, 250);

        mDescriptionView.setText(local.getDescription());
        mRatingBar.setRating(4.3f);

        Glide.with(this.getContext())
                .load(local.getImagePath())
                .into(mPhotoView);

        CommonUtils.showViewLayout(getContext(), mLocalView);
        mLocalView.setTag(local);
    }

    @Override
    public void showLocal(Local local) {
        LocalDetailActivity.navigate(this.getActivity(), local);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActionsListener.unsubscribe();
    }
}
