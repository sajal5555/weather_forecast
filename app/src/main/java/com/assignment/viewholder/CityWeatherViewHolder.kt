package com.assignment.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemCityWeatherDetailBinding
import com.assignment.models.responsemodel.WeatherDataModel
import com.assignment.viewmodel.CityWeatherInformationViewModel

class CityWeatherViewHolder private constructor(
    private var binding: ItemCityWeatherDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(viewModel: CityWeatherInformationViewModel?, weatherDataModel: WeatherDataModel?) {
        binding.viewModel = viewModel
        binding.item = weatherDataModel
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): CityWeatherViewHolder {
            val binding =
                ItemCityWeatherDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return CityWeatherViewHolder(binding)
        }
    }
}