package com.assignment.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemMapLocationsListBinding
import com.assignment.viewmodel.MapLocationsViewModel

class MapLocationsViewHolder private constructor(
    private var binding: ItemMapLocationsListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(viewModel: MapLocationsViewModel?, cityName: String) {
        binding.viewModel = viewModel
        binding.city = cityName
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): MapLocationsViewHolder {
            val binding =
                ItemMapLocationsListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return MapLocationsViewHolder(binding)
        }
    }
}