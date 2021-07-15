package com.assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.assignment.R
import com.assignment.fragment.BookMarkFragment
import com.assignment.fragment.MapLocationsFragment
import com.assignment.fragment.TopCitiesFragment

val TAB_TITLES = arrayOf(
    R.string.tab_top_cities,
    R.string.tab_map_locations,
    R.string.tab_book_mark
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class TabsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val map = HashMap<Int, Fragment>()

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                val fragment = TopCitiesFragment.newInstance()
                map[position] = fragment
                fragment
            }

            1 -> {
                val fragment = MapLocationsFragment.newInstance()
                map[position] = fragment
                fragment
            }
            else -> {
                val fragment = BookMarkFragment.newInstance()
                map[position] = fragment
                fragment
            }
        }
    }
}