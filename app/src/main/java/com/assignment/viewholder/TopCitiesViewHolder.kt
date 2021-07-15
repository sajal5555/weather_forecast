package com.assignment.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemTopCitiesListBinding
import com.assignment.viewmodel.TopCitiesViewModel

class TopCitiesViewHolder private constructor(
    private var binding: ItemTopCitiesListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(viewModel: TopCitiesViewModel?, cityName: String) {
        binding.viewModel = viewModel
        binding.city = cityName
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): TopCitiesViewHolder {
            val binding =
                ItemTopCitiesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopCitiesViewHolder(binding)
        }
    }
}