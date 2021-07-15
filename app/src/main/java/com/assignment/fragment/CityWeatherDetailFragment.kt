package com.assignment.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.assignment.BR
import com.assignment.R
import com.assignment.adapter.WeatherForecastAdapter
import com.assignment.databinding.FragmentCityWeatherDetailBinding
import com.assignment.viewmodel.CityWeatherInformationViewModel
import kotlinx.android.synthetic.main.fragment_city_weather_detail.*
import kotlinx.android.synthetic.main.fragment_topcities.*

/**
 * A placeholder fragment containing a simple view.
 */
class CityWeatherDetailFragment :
    BaseFragment<FragmentCityWeatherDetailBinding, CityWeatherInformationViewModel>() {
    private var adapter: WeatherForecastAdapter? = null

    override fun initializeLayoutId(): Int {
        return R.layout.fragment_city_weather_detail
    }

    override fun getViewModel(): CityWeatherInformationViewModel {
        val model: CityWeatherInformationViewModel by activityViewModels()
        return model
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun initializeController() {
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        initAdapter(arguments?.getInt("pageNum") ?: 0)
    }

    private fun initAdapter(pageNum: Int) {
        if (adapter != null) {
            adapter?.notifyDataSetChanged()

        } else {

            adapter = WeatherForecastAdapter(getViewModel(), pageNum)
            city_weather_list.apply {
                setHasFixedSize(true)
                adapter = this@CityWeatherDetailFragment.adapter
            }
        }
    }


    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(page: Int): CityWeatherDetailFragment {
            val fragment = CityWeatherDetailFragment()
            val bundle = Bundle()
            bundle.putInt("pageNum", page)
            fragment.arguments = bundle
            return fragment
        }
    }
}