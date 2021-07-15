package com.assignment.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.assignment.BR
import com.assignment.R
import com.assignment.databinding.ActivityHomeBinding
import com.assignment.fragment.HomeFragment
import com.assignment.models.constants.Constants
import com.assignment.utilities.SharedPrefUtil
import com.assignment.viewmodel.HomeViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun getLayoutId() = R.layout.activity_home

    override fun getBindingVariable() = BR.viewModel

    override fun initializeViews(bundle: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Weather"

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commit()

        val myResArray = resources.getStringArray(R.array.listArray)
        SharedPrefUtil.storeSharedPref(
            Constants.PREF_UNIT_SYSTEM,
            myResArray[0]
        )
    }

    override fun onResume() {
        super.onResume()

        if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            askPermission(
                1,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION

            )
        } else {
            fetchLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            getViewModel().currentLocation.value = location
            val address = location?.let {
                Geocoder(this, Locale.getDefault()).getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
            }
            // fetch weather detail
            getViewModel().location.value = address?.get(0)?.subAdminArea
            getViewModel().getCityWeather(address?.get(0)?.postalCode)
        }
    }

    override fun requestPermissionSuccess(type: Int) {
        if (type == 1) {
            fetchLocation()
        }

    }

    override fun getViewModel(): HomeViewModel {
        val model: HomeViewModel by viewModels()
        return model

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val aboutIntent = Intent(this@HomeActivity, WeatherSettingActivity::class.java)
                startActivity(aboutIntent)
            }
            R.id.help -> {
                val aboutIntent = Intent(this@HomeActivity, HelpActivity::class.java)
                startActivity(aboutIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}