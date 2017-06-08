package com.guideapp.ui.views.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.guideapp.R
import com.guideapp.model.Local
import com.guideapp.ui.views.localdetail.LocalDetailActivity
import com.guideapp.utilities.Constants
import com.guideapp.utilities.Utility
import com.guideapp.utilities.ViewUtil

import java.util.HashMap

class MapFragment : Fragment(), OnMapReadyCallback, MapContract.View {
    private var mSupportMapFragment: SupportMapFragment? = null
    private var mDescriptionSubCategoryView: TextView? = null
    private var mLocalView: RelativeLayout? = null
    private var mMap: GoogleMap? = null
    private var mPresenter: MapContract.Presenter? = null
    private var mDescriptionView: TextView? = null
    private var mPhotoView: ImageView? = null
    private var mMarkersId: HashMap<String, Local>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)
        setupViews(view)
        setupViewProperties()
        mMarkersId = HashMap<String, Local>()
        mPresenter = MapPresenter(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMapIfNeeded()
    }

    private fun setupViews(view: View) {
        mLocalView = view.findViewById(R.id.local_view) as RelativeLayout
        mDescriptionSubCategoryView = mLocalView?.findViewById(R.id.descriptions_sub_category) as TextView
        mDescriptionView = mLocalView?.findViewById(R.id.local_description) as TextView
        mPhotoView = mLocalView?.findViewById(R.id.local_picture) as ImageView
    }

    private fun setupViewProperties() {
        mLocalView?.setOnClickListener { v -> mPhotoView?.let { mPresenter?.openLocalDetails(v.tag as Local, it) } }
    }

    private fun setUpMapIfNeeded() {
        if (mSupportMapFragment == null) {
            val fm = childFragmentManager
            mSupportMapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment
            mSupportMapFragment?.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val center = LatLng(Constants.City.LATITUDE, Constants.City.LONGITUDE)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12f))
        map.setOnMarkerClickListener { marker ->
            mPresenter?.openLocalSummary(mMarkersId?.get(marker.id))
            false
        }

        mPresenter?.loadLocals(activity.supportLoaderManager)
    }

    override fun showLocals(locals: List<Local>) {
        var latLng: LatLng
        var marker: Marker?
        mMarkersId?.clear()

        for (local in locals) {
            latLng = LatLng(local.latitude, local.longitude)

            val markerOptions = MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(Utility.getIdImageCategory(local.idCategory)))

            marker = mMap?.addMarker(markerOptions)
            marker?.let { m -> mMarkersId?.put(m.id, local) }
        }
    }

    override fun showLocalSummary(local: Local) {
        mMap?.setPadding(0, 0, 0, 250)
        mDescriptionView?.text = local.description
        mDescriptionSubCategoryView?.text = local.descriptionSubCategories

        Glide.with(this.context)
                .load(local.imagePath)
                .placeholder(R.color.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mPhotoView)

        mLocalView?.let { ViewUtil.showViewLayout(context, it) }
        mLocalView?.tag = local
    }

    override fun showLocalDetailUi(local: Local, view: ImageView) {
        LocalDetailActivity.navigate(this.activity, view, local.id!!, local.idCategory)
    }

    companion object {
        private val EXTRA_CITY = "id-city"
        private val EXTRA_CATEGORY = "id-category"
        private val EXTRA_SUB_CATEGORY = "id-sub-category"

        fun newInstance(idCity: Long, idCategory: Long, idSubCategories: LongArray?): Fragment {
            val fragment = MapFragment()
            val bundle = Bundle()
            bundle.putLong(EXTRA_CITY, idCity)
            bundle.putLong(EXTRA_CATEGORY, idCategory)
            bundle.putLongArray(EXTRA_SUB_CATEGORY, idSubCategories)
            fragment.arguments = bundle
            return fragment
        }
    }

}
