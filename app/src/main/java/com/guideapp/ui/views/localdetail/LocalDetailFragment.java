package com.guideapp.ui.views.localdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.guideapp.R;
import com.guideapp.utilities.Constants;

public class LocalDetailFragment extends Fragment
        implements LocalDetailContract.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final String EXTRA_LOCAL_ID = "local_id";

    private double mLatitude;
    private double mLongitude;
    private int mIdImageMarker;

    private CollapsingToolbarLayout mCollapsing;
    private FloatingActionButton mFab;
    private ImageView mImage;
    private LocalDetailContract.Presenter mPresenter;

    private TextView mSubCategoryView;
    private TextView mDescriptionView;
    private TextView mDirectionActionView;
    private TextView mCallActionView;
    private TextView mWebsiteActionView;
    private TextView mAddressView;
    private TextView mPhoneView;

    private SupportMapFragment mSupportMapFragment;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String mTitle;

    public static Fragment newInstance(long id) {
        Fragment fragment = new LocalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_LOCAL_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_detail, container, false);

        setupViews(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                mPresenter.shareLocal();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViews(View view) {
        mCollapsing = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsingToolbarLayout);
        mImage = (ImageView) getActivity().findViewById(R.id.image);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mSubCategoryView = (TextView) view.findViewById(R.id.sub_category_text);
        mDescriptionView = (TextView) view.findViewById(R.id.description_text);
        mAddressView = (TextView) view.findViewById(R.id.address_text);
        mPhoneView = (TextView) view.findViewById(R.id.phone_text);

        mDirectionActionView = (TextView) view.findViewById(R.id.direction_action);
        mCallActionView = (TextView) view.findViewById(R.id.call_action);
        mWebsiteActionView = (TextView) view.findViewById(R.id.website_action);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveOrRemoveFavorite();

            }
        });

        mAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadDirection();

            }
        });

        mDirectionActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadDirection();
            }
        });

        mPhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadCall();
            }
        });

        mCallActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadCall();
            }
        });

        mWebsiteActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadWebsite();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) {
            mPresenter.destroy(getActivity().getSupportLoaderManager());
            mPresenter = null;
        }
    }

    @Override
    public void showFavoriteYes() {
        mFab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_white_24dp));
    }

    @Override
    public void showFavoriteNo() {
        mFab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_border_white_24dp));
    }

    @Override
    public void showSnackbarRemoveFavorite() {
        Snackbar.make(mCollapsing, R.string.title_remove_favorite, Snackbar.LENGTH_SHORT).show();
        sendEventFavorite(Constants.Analytics.REMOVE_FAVORITE);
    }

    @Override
    public void showSnackbarSaveFavorite() {
        Snackbar.make(mCollapsing, R.string.title_save_favorite, Snackbar.LENGTH_SHORT).show();
        sendEventFavorite(Constants.Analytics.SAVE_FAVORITE);
    }

    private void sendEventFavorite(String type) {
        Bundle bundle = new Bundle();

        if (getArguments() != null) {
            long id = getArguments().getLong(EXTRA_LOCAL_ID);
            bundle.putLong(FirebaseAnalytics.Param.ITEM_ID, id);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mTitle);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            long id = getArguments().getLong(EXTRA_LOCAL_ID);

            mPresenter = new LocalDetailPresenter(this, id);
            mPresenter.loadLocal(getActivity().getSupportLoaderManager());
        }
    }

    @Override
    public void shareText(String textToShare) {
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this.getActivity())
                .setType(mimeType)
                .setChooserTitle(R.string.app_name)
                .setText(textToShare)
                .startChooser();
    }

    @Override
    public void showTitle(String description) {
        mTitle = description;
        mCollapsing.setTitle(description);
        mCollapsing.setExpandedTitleColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    @Override
    public void showImage(String imagePath) {
        Glide.with(this)
                .load(imagePath)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(mImage) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        mImage.setBackgroundResource(R.drawable.action_background_bottom);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }
                });
    }

    @Override
    public void showCategory(String text) {
        mSubCategoryView.setText(text);
    }

    @Override
    public void showWebSiteAction() {
        mWebsiteActionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDirectionAction() {
        mDirectionActionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCallAction() {
        mCallActionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCall(String phone) {
        mPhoneView.setVisibility(View.VISIBLE);
        mPhoneView.setText(phone);
    }

    @Override
    public void showDetail(String description) {
        mDescriptionView.setVisibility(View.VISIBLE);
        mDescriptionView.setText(description);
    }

    @Override
    public void showAddress(String address) {
        mAddressView.setVisibility(View.VISIBLE);
        mAddressView.setText(address);
    }

    @Override
    public void showMap(double latitude, double longitude, int idImageMarker) {
        mLatitude = latitude;
        mLongitude = longitude;
        mIdImageMarker = idImageMarker;
        setUpMapIfNeeded();

        getActivity().supportStartPostponedEnterTransition();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng center = new LatLng(mLatitude, mLongitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(center));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(center)
                .icon(BitmapDescriptorFactory.fromResource(mIdImageMarker));

        googleMap.clear();
        googleMap.addMarker(markerOptions);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mPresenter.loadDirection();

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

    private void applyPalette(Palette palette) {
        int primary = ContextCompat.getColor(getContext(), R.color.blue_grey_500);
        mCollapsing.setContentScrimColor(palette.getVibrantColor(primary));
        mCollapsing.setStatusBarScrimColor(palette.getVibrantColor(primary));
    }

    @Override
    public void dialPhoneNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void openPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void openDirection(String description, String latLng) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + latLng + "?q=" + latLng + "(" + description + ")"));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mPresenter.loadDirection();
        return false;
    }
}
