package com.guideapp.guideapp.ui.views.photoview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenuTemp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class PhotoViewerPageFragment extends Fragment {
    public static final String ARG_PAGE = "page";

    private int mPageNumber;

    private static  List<MainMenuTemp> mDataSet = new ArrayList<>();

    static
    {
        for (int i = 1; i <= 10; i++) {
            mDataSet.add(new MainMenuTemp(i, "http://lorempixel.com/500/500/animals/" + i));
        }
    }

    public static PhotoViewerPageFragment create(int pageNumber) {
        PhotoViewerPageFragment fragment = new PhotoViewerPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoViewerPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.item_photo_viewer, container, false);

        final ImageViewTouch imageView = (ImageViewTouch) rootView.findViewById(R.id.image);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        Picasso.with(this.getActivity())
                .load(mDataSet.get(mPageNumber).getText())
                .into(imageView);

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
