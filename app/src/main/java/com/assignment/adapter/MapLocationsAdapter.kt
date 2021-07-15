package com.assignment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.viewholder.MapLocationsViewHolder
import com.assignment.viewmodel.MapLocationsViewModel

class MapLocationsAdapter(var viewModel: MapLocationsViewModel?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ArrayList<String?>? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return MapLocationsViewHolder.create(parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MapLocationsViewHolder).bindTo(
            viewModel = viewModel,
            cityName = data?.get(position) ?: ""
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() =
        data?.size ?: 0

    fun setData(data: ArrayList<String?>?) {
        this.data = data
    }
}