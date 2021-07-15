package com.assignment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.viewholder.BookmarkViewHolder
import com.assignment.viewmodel.BookmarkViewModel

class BookmarkAdapter(var viewModel: BookmarkViewModel?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookmarkData: ArrayList<String?>? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return BookmarkViewHolder.create(parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookmarkViewHolder).bindTo(
            viewModel = viewModel,
            cityName = bookmarkData?.get(position) ?: ""
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() =
        bookmarkData?.size ?: 0

    fun setData(bookmarkData: ArrayList<String?>?) {
        this.bookmarkData = bookmarkData
    }
}