package com.assignment.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.assignment.BR
import com.assignment.R
import com.assignment.adapter.MapLocationsAdapter
import com.assignment.databinding.FragmentMaplocationsBinding
import com.assignment.models.constants.Constants
import com.assignment.viewmodel.MapLocationsViewModel
import com.assignment.utilities.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_maplocations.*
import java.util.*


/**
 * A placeholder fragment containing a simple view.
 */
class MapLocationsFragment : BaseFragment<FragmentMaplocationsBinding, MapLocationsViewModel>() {
    private var adapter: MapLocationsAdapter? = null

    override fun initializeLayoutId(): Int {
        return R.layout.fragment_maplocations
    }

    override fun getViewModel(): MapLocationsViewModel {
        val model: MapLocationsViewModel by activityViewModels()
        return model
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun initializeController() {
        val mapLocationsData =
            SharedPrefUtil.getListSharedPref(
                Constants.PREF_MAP_LOCATIONS
            ) as ArrayList<String?>?
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
                    initAdapter(mapLocationsData)
                } else {
                    val data: ArrayList<String?>? =
                        mapLocationsData?.filter {
                            it?.toUpperCase(Locale.getDefault())
                                ?.startsWith(s.toString().toUpperCase(Locale.getDefault())) ?: false
                        } as ArrayList<String?>?
                    initAdapter(data)
                }
            }
        })
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        val mapLocationsData =
            SharedPrefUtil.getListSharedPref(
                Constants.PREF_MAP_LOCATIONS
            ) as ArrayList<String?>?

        initAdapter(mapLocationsData)

        initObservers()
    }

    private fun initObservers() {
        getViewModel().cancelClickLiveData.observe(this.viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer

            // refresh adapter
            initAdapter(SharedPrefUtil.removeMapLocationEntry(it))

            Toast.makeText(activity, "`$it` removed from list", Toast.LENGTH_SHORT).show()

        })

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
            val mapLocationData =
                SharedPrefUtil.getListSharedPref(
                    Constants.PREF_MAP_LOCATIONS
                ) as ArrayList<String?>?
            initAdapter(mapLocationData)

        })
    }

    fun initAdapter(data: ArrayList<String?>?) {
        if (adapter != null) {
            adapter?.setData(data)
            adapter?.notifyDataSetChanged()

        } else {
            adapter = MapLocationsAdapter(getViewModel())
            adapter?.setData(data)
            map_locations_list.apply {
                setHasFixedSize(true)
                adapter = this@MapLocationsFragment.adapter
            }
        }
    }

    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): MapLocationsFragment {
            return MapLocationsFragment()
        }
    }
}