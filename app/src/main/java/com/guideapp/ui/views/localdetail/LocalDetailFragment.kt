package com.guideapp.ui.views.localdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.ShareCompat
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.guideapp.R
import com.guideapp.utilities.Constants

class LocalDetailFragment : Fragment(), LocalDetailContract.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var mLatitude: Double = 0.toDouble()
    private var mLongitude: Double = 0.toDouble()
    private var mIdImageMarker: Int = 0

    private var mCollapsing: CollapsingToolbarLayout? = null
    private var mFab: FloatingActionButton? = null
    private var mImage: ImageView? = null
    private var mPresenter: LocalDetailContract.Presenter? = null

    private var mSubCategoryView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mDirectionActionView: TextView? = null
    private var mCallActionView: TextView? = null
    private var mWebsiteActionView: TextView? = null
    private var mAddressView: TextView? = null
    private var mPhoneView: TextView? = null

    private var mSupportMapFragment: SupportMapFragment? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mTitle: String? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_local_detail, container, false)
        setupViews(view)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        when (id) {
            R.id.action_share -> mPresenter!!.shareLocal()

            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupViews(view: View) {
        mCollapsing = activity.findViewById(R.id.collapsingToolbarLayout) as CollapsingToolbarLayout
        mImage = activity.findViewById(R.id.image) as ImageView
        mFab = activity.findViewById(R.id.fab) as FloatingActionButton

        mSubCategoryView = view.findViewById(R.id.sub_category_text) as TextView
        mDescriptionView = view.findViewById(R.id.description_text) as TextView
        mAddressView = view.findViewById(R.id.address_text) as TextView
        mPhoneView = view.findViewById(R.id.phone_text) as TextView

        mDirectionActionView = view.findViewById(R.id.direction_action) as TextView
        mCallActionView = view.findViewById(R.id.call_action) as TextView
        mWebsiteActionView = view.findViewById(R.id.website_action) as TextView

        mFab!!.setOnClickListener { mPresenter!!.saveOrRemoveFavorite() }
        mAddressView!!.setOnClickListener { mPresenter!!.loadDirection() }
        mDirectionActionView!!.setOnClickListener { mPresenter!!.loadDirection() }
        mPhoneView!!.setOnClickListener { mPresenter!!.loadCall() }
        mCallActionView!!.setOnClickListener { mPresenter!!.loadCall() }
        mWebsiteActionView!!.setOnClickListener { mPresenter!!.loadWebsite() }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (mPresenter != null) {
            mPresenter!!.destroy(activity.supportLoaderManager)
            mPresenter = null
        }
    }

    override fun showFavoriteYes() {
        mFab!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_white_24dp))
    }

    override fun showFavoriteNo() {
        mFab!!.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_border_white_24dp))
    }

    override fun showSnackbarRemoveFavorite() {
        Snackbar.make(mCollapsing!!, R.string.title_remove_favorite, Snackbar.LENGTH_SHORT).show()
        sendEventFavorite(Constants.Analytics.REMOVE_FAVORITE)
    }

    override fun showSnackbarSaveFavorite() {
        Snackbar.make(mCollapsing!!, R.string.title_save_favorite, Snackbar.LENGTH_SHORT).show()
        sendEventFavorite(Constants.Analytics.SAVE_FAVORITE)
    }

    private fun sendEventFavorite(type: String) {
        val bundle = Bundle()

        if (arguments != null) {
            val id = arguments.getLong(EXTRA_LOCAL_ID)
            bundle.putLong(FirebaseAnalytics.Param.ITEM_ID, id)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mTitle)
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type)
            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val id = arguments.getLong(EXTRA_LOCAL_ID)

            mPresenter = LocalDetailPresenter(this, id)
            mPresenter!!.loadLocal(activity.supportLoaderManager)
        }
    }

    override fun shareText(textToShare: String) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
                .from(this.activity)
                .setType(mimeType)
                .setChooserTitle(R.string.app_name)
                .setText(textToShare)
                .startChooser()
    }

    override fun showTitle(description: String) {
        mTitle = description
        mCollapsing!!.title = description
        mCollapsing!!.setExpandedTitleColor(ContextCompat.getColor(context, R.color.white))
    }

    override fun showImage(imagePath: String) {
        Glide.with(this)
                .load(imagePath)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : BitmapImageViewTarget(mImage!!) {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>?) {
                        super.onResourceReady(resource, glideAnimation)
                        mImage!!.setBackgroundResource(R.drawable.action_background_bottom)
                        Palette.from(resource).generate { palette -> applyPalette(palette) }
                    }
                })
    }

    override fun showCategory(text: String) {
        mSubCategoryView!!.text = text
    }

    override fun showWebSiteAction() {
        mWebsiteActionView!!.visibility = View.VISIBLE
    }

    override fun showDirectionAction() {
        mDirectionActionView!!.visibility = View.VISIBLE
    }

    override fun showCallAction() {
        mCallActionView!!.visibility = View.VISIBLE
    }

    override fun showCall(phone: String) {
        mPhoneView!!.visibility = View.VISIBLE
        mPhoneView!!.text = phone
    }

    override fun showDetail(description: String) {
        mDescriptionView!!.visibility = View.VISIBLE
        mDescriptionView!!.text = description
    }

    override fun showAddress(address: String) {
        mAddressView!!.visibility = View.VISIBLE
        mAddressView!!.text = address
    }

    override fun showMap(latitude: Double, longitude: Double, idImageMarker: Int) {
        mLatitude = latitude
        mLongitude = longitude
        mIdImageMarker = idImageMarker
        setUpMapIfNeeded()
        activity.supportStartPostponedEnterTransition()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val center = LatLng(mLatitude, mLongitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(center))

        val markerOptions = MarkerOptions()
                .position(center)
                .icon(BitmapDescriptorFactory.fromResource(mIdImageMarker))

        googleMap.clear()
        googleMap.addMarker(markerOptions)
        googleMap.uiSettings.isMapToolbarEnabled = false

        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMapClickListener { mPresenter!!.loadDirection() }
    }

    private fun setUpMapIfNeeded() {
        if (mSupportMapFragment == null) {
            val fm = childFragmentManager
            mSupportMapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment
            mSupportMapFragment!!.getMapAsync(this)
        }
    }

    private fun applyPalette(palette: Palette) {
        val primary = ContextCompat.getColor(context, R.color.blue_grey_500)
        mCollapsing!!.setContentScrimColor(palette.getVibrantColor(primary))
        mCollapsing!!.setStatusBarScrimColor(palette.getVibrantColor(primary))
    }

    override fun dialPhoneNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + number)
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun openPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun openDirection(description: String, latLng: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:$latLng?q=$latLng($description)")
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        mPresenter!!.loadDirection()
        return false
    }

    companion object {
        private val EXTRA_LOCAL_ID = "local_id"

        fun newInstance(id: Long): Fragment {
            val fragment = LocalDetailFragment()
            val bundle = Bundle()
            bundle.putLong(EXTRA_LOCAL_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
