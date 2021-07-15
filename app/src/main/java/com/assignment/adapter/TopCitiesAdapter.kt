package com.assignment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.models.CityEnum
import com.assignment.viewholder.TopCitiesViewHolder
import com.assignment.viewmodel.TopCitiesViewModel

/**
* @author Sajal Jain
* @version 1.0
* @since 13.07.2021
*/
class TopCitiesAdapter(var viewModel: TopCitiesViewModel?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: MutableList<CityEnum>? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return TopCitiesViewHolder.create(parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopCitiesViewHolder).bindTo(
            viewModel,
            data?.get(position)?.name ?: ""
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data?.size ?: 0

    fun setListData(data: MutableList<CityEnum>) {
        this.data = data
    }
}