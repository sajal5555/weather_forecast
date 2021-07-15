package com.assignment.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.assignment.R
import com.assignment.repository.Constants
import com.assignment.utilities.SharedPrefUtil
const val KEY_LIST_PREFERENCE = "listPref"

class WeatherSettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private var mListPreference: ListPreference? = null
    private var preference: Preference? = null
    private var mapLocationsPreference: Preference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_main, rootKey)

        // Get a reference to the preferences
        mListPreference = findPreference(KEY_LIST_PREFERENCE)
        preference = findPreference("reset_bookmark")
        mapLocationsPreference = findPreference("reset_map_locations")
        preference?.onPreferenceClickListener = this
        mapLocationsPreference?.onPreferenceClickListener = this

        if (mListPreference?.value == null) {
            val myResArray = resources.getStringArray(R.array.listArray)
            mListPreference?.setDefaultValue(myResArray[0])
        }

    }

    override fun onResume() {
        super.onResume()

        mListPreference?.summary = mListPreference?.entry?.toString()

        // Set up a listener whenever a key changes
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        // Unregister the listener whenever a key changes
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        // Set new summary, when a preference value changes
        if (key == KEY_LIST_PREFERENCE) {
            mListPreference?.summary = mListPreference?.entry?.toString()
            SharedPrefUtil.storeSharedPref(
                Constants.PREF_UNIT_SYSTEM,
                mListPreference?.summary?.toString()
            )
        }
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            "reset_map_locations" -> {
                // Clear map locations
                val data =
                    SharedPrefUtil.getListSharedPref(
                        Constants.PREF_MAP_LOCATIONS
                    ) as ArrayList<String?>?
                data?.clear()

                SharedPrefUtil.storeListSharedPref(
                    Constants.PREF_MAP_LOCATIONS,
                    data
                )
                Toast.makeText(context, "All map locations are cleared.", Toast.LENGTH_SHORT).show()

            }
            "reset_bookmark" -> {
                // Clear bookmarks
                val bookmarkData =
                    SharedPrefUtil.getListSharedPref(
                        Constants.PREF_BOOKMARK
                    ) as ArrayList<String?>?
                bookmarkData?.clear()

                SharedPrefUtil.storeListSharedPref(
                    Constants.PREF_BOOKMARK,
                    bookmarkData
                )
                Toast.makeText(context, "All bookmarks are cleared.", Toast.LENGTH_SHORT).show()

            }
        }

        return true
    }
}