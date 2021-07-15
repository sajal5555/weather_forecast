package com.assignment.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.assignment.BR
import com.assignment.R
import com.assignment.adapter.TopCitiesAdapter
import com.assignment.databinding.FragmentTopcitiesBinding
import com.assignment.models.CityEnum
import com.assignment.utilities.SharedPrefUtil
import com.assignment.viewmodel.TopCitiesViewModel
import kotlinx.android.synthetic.main.fragment_maplocations.*
import kotlinx.android.synthetic.main.fragment_topcities.*
import kotlinx.android.synthetic.main.fragment_topcities.search_bar
import java.util.*
import kotlin.collections.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
class TopCitiesFragment : BaseFragment<FragmentTopcitiesBinding, TopCitiesViewModel>() {
    private var adapter: TopCitiesAdapter? = null

    override fun initializeLayoutId(): Int {
        return R.layout.fragment_topcities
    }

    override fun getViewModel(): TopCitiesViewModel {
        val model: TopCitiesViewModel by activityViewModels()
        return model
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun initializeController() {
        search_bar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isBlank()) {
                    initAdapter(CityEnum.values().toMutableList())
                } else {
                    val data: ArrayList<CityEnum> =
                        CityEnum.values().filter {
                            it.name.toUpperCase(Locale.getDefault())
                                .startsWith(s.toString().toUpperCase(Locale.getDefault()))
                        } as ArrayList<CityEnum>
                    initAdapter(data)
                }
            }
        })
    }


    override fun initializeViews(savedInstanceState: Bundle?) {
        initObservers()
    }

    private fun initObservers() {
        getViewModel().bookmarkClickLiveData.observe(this.viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer

            if (SharedPrefUtil.bookmarkAlreadyStored(it))
                Toast.makeText(context, "`$it` is already bookmarked", Toast.LENGTH_SHORT)
                    .show()
            else {
                SharedPrefUtil.addBookmarkEntry(it)
                Toast.makeText(context, "`$it` is bookmarked", Toast.LENGTH_SHORT).show()
            }

            // refresh adapter
            initAdapter(CityEnum.values().toMutableList())

        })
    }


    override fun onResume() {
        super.onResume()

        initAdapter(CityEnum.values().toMutableList())
    }

    private fun initAdapter(data: MutableList<CityEnum>) {
        if (adapter != null) {
            adapter?.setListData(data)
            adapter?.notifyDataSetChanged()

        } else {

            adapter = TopCitiesAdapter(getViewModel())
            adapter?.setListData(data)
            top_cities_list.apply {
                setHasFixedSize(true)
                adapter = this@TopCitiesFragment.adapter
            }
        }
    }


    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): TopCitiesFragment {
            return TopCitiesFragment()
        }
    }
}