package com.assignment.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.ItemBookmarkListBinding
import com.assignment.viewmodel.BookmarkViewModel

class BookmarkViewHolder private constructor(
    private var binding: ItemBookmarkListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(viewModel: BookmarkViewModel?, cityName: String) {
        binding.viewModel = viewModel
        binding.city = cityName
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): BookmarkViewHolder {
            val binding =
                ItemBookmarkListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BookmarkViewHolder(binding)
        }
    }
}