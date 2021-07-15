package com.assignment.fragment

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.assignment.BR
import com.assignment.R
import com.assignment.adapter.TAB_TITLES
import com.assignment.adapter.TabsPagerAdapter
import com.assignment.databinding.FragmentHomeBinding
import com.assignment.models.constants.Constants
import com.assignment.utilities.SharedPrefUtil
import com.assignment.utilities.observeOnce
import com.assignment.viewmodel.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_topcities.*
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), OnMapReadyCallback {
    private lateinit var adapter: TabsPagerAdapter
    private lateinit var mMap: GoogleMap
    private var currentItem = 0
    override fun initializeLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TabsPagerAdapter(this)
        pager.adapter = adapter
        pager.offscreenPageLimit = TAB_TITLES.size

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = context?.resources?.getString(TAB_TITLES[position]) ?: ""
        }.attach()
        pager.registerOnPageChangeCallback(callback)


        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        initObservers()
    }

    private fun initObservers() {
        getViewModel().currentLocation.observeOnce {
            if (it == null)
                return@observeOnce

            val currentPosition = LatLng(it.latitude, it.longitude)
            mMap.addMarker(
                MarkerOptions()
                    .position(currentPosition)
                    .title("You are here")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { location ->
            //  allPoints.add(it)
            val address = Geocoder(context, Locale.getDefault()).getFromLocation(
                location.latitude,
                location.longitude,
                1
            )

            val area = address?.get(0)?.subAdminArea
            SharedPrefUtil.addMapLocationEntry(area)

            mMap.clear()
            mMap.addMarker(MarkerOptions().position(location))

            if (!area.isNullOrBlank())
                Toast.makeText(activity, "`$area` added in the list", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onResume() {
        super.onResume()
        if (adapter.map.size > 0) {
            refreshPage(1, 2, 3)
        }
    }

    private var callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            refreshPage(position)
            super.onPageSelected(position)
        }
    }

    override fun onStop() {
        super.onStop()
        currentItem = pager.currentItem
    }

    private fun refreshPage(vararg positions: Int) {
        for (position in positions) {
            when (val fragment = adapter.map[position]) {
                is BookMarkFragment -> {
                    val bookmarkData =
                        SharedPrefUtil.getListSharedPref(
                            Constants.PREF_BOOKMARK
                        ) as ArrayList<String?>?
                    fragment.initAdapter(bookmarkData)
                }
                is MapLocationsFragment -> {
                    val bookmarkData =
                        SharedPrefUtil.getListSharedPref(
                            Constants.PREF_MAP_LOCATIONS
                        ) as ArrayList<String?>?

                    fragment.initAdapter(bookmarkData)
                    fragment.search_bar.setText("")

                }
                is TopCitiesFragment -> {
                    fragment.search_bar.setText("")

                }
            }
        }
    }

    override fun getViewModel(): HomeViewModel {
        val model: HomeViewModel by activityViewModels()
        return model
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun initializeController() {
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        pager.unregisterOnPageChangeCallback(callback)
    }

    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}