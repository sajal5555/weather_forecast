package com.assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.assignment.fragment.CityWeatherDetailFragment
import com.assignment.models.responsemodel.WeatherDataModel
import com.assignment.utilities.DateTimeUtil
import java.util.ArrayList


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class WeatherForecastTabsPagerAdapter(
    private var tabsDetail: ArrayList<List<WeatherDataModel>>,
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return CityWeatherDetailFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return DateTimeUtil.getDay(tabsDetail[position][0].dt)
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return tabsDetail.size
    }
}