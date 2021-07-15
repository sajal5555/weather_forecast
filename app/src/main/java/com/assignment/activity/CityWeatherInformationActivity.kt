package com.assignment.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.assignment.BR
import com.assignment.R
import com.assignment.adapter.WeatherForecastTabsPagerAdapter
import com.assignment.databinding.ActivityCityWeatherInformationBinding
import com.assignment.utilities.observeOnce
import com.assignment.viewmodel.CityWeatherInformationViewModel
import kotlinx.android.synthetic.main.activity_city_weather_information.*

/**
 * @author Sajal Jain
 * @version 1.0
 * @since 13.07.2021
 */
class CityWeatherInformationActivity :
    BaseActivity<ActivityCityWeatherInformationBinding, CityWeatherInformationViewModel>() {

    override fun getLayoutId() = R.layout.activity_city_weather_information

    override fun getBindingVariable() = BR.viewModel

    override fun initializeViews(bundle: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Weather Forecast"

        val cityName: String? = intent?.extras?.getString("cityName")
        getViewModel().getCityWeather(cityName)

        supportActionBar?.subtitle = cityName
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        initObservers()

    }

    private fun initObservers() {
        getViewModel().cityWeatherDetailLiveData.observeOnce {
            view_pager.adapter = WeatherForecastTabsPagerAdapter(it, supportFragmentManager)

            tabs.setupWithViewPager(view_pager)
            view_pager.offscreenPageLimit = it.size

        }
    }

    override fun getViewModel(): CityWeatherInformationViewModel {
        val model: CityWeatherInformationViewModel by viewModels()
        return model
    }
}