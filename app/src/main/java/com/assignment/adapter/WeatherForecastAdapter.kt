package com.assignment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.viewholder.CityWeatherViewHolder
import com.assignment.viewmodel.CityWeatherInformationViewModel

class WeatherForecastAdapter(var viewModel: CityWeatherInformationViewModel?, private var pageNum: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return CityWeatherViewHolder.create(parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CityWeatherViewHolder).bindTo(
            viewModel,
            viewModel?.cityWeatherDetailLiveData?.value?.get(pageNum)?.get(position)
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() =
        viewModel?.cityWeatherDetailLiveData?.value?.get(pageNum)?.size ?: 0
}