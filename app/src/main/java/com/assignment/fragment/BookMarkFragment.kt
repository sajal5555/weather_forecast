package com.assignment.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.assignment.BR
import com.assignment.R
import com.assignment.adapter.BookmarkAdapter
import com.assignment.databinding.FragmentBookmarkBinding
import com.assignment.models.constants.Constants
import com.assignment.utilities.SharedPrefUtil
import com.assignment.viewmodel.BookmarkViewModel
import kotlinx.android.synthetic.main.fragment_topcities.*

/**
 * A placeholder fragment containing a simple view.
 */
class BookMarkFragment : BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>() {
    private var adapter: BookmarkAdapter? = null

    override fun initializeLayoutId(): Int {
        return R.layout.fragment_bookmark
    }

    override fun getViewModel(): BookmarkViewModel {
        val model: BookmarkViewModel by activityViewModels()
        return model
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun initializeController() {
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        val bookmarkData =
            SharedPrefUtil.getListSharedPref(
                Constants.PREF_BOOKMARK
            ) as ArrayList<String?>?

        initAdapter(bookmarkData)

        initObservers()
    }

    private fun initObservers() {
        getViewModel().cancelClickLiveData.observe(this.viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer

            // refresh adapter
            initAdapter(SharedPrefUtil.removeBookmarkEntry(it))

            Toast.makeText(activity, "`$it` removed from bookmark", Toast.LENGTH_SHORT).show()

        })
    }

    fun initAdapter(bookmarkData: ArrayList<String?>?) {
        if (adapter != null) {
            adapter?.setData(bookmarkData)
            adapter?.notifyDataSetChanged()

        } else {
            adapter = BookmarkAdapter(getViewModel())
            adapter?.setData(bookmarkData)
            top_cities_list.apply {
                setHasFixedSize(true)
                adapter = this@BookMarkFragment.adapter
            }
        }
    }

    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): BookMarkFragment {
            return BookMarkFragment()
        }
    }
}