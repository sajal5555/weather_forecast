package com.assignment.utilities

import android.content.Context
import com.assignment.CustomApplication
import com.assignment.R
import com.assignment.repository.Constants
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList


class SharedPrefUtil {

    companion object {
        fun storeListSharedPref(key: String, value: ArrayList<String?>?) {
            val sharedPreferences = CustomApplication.applicationContext().getSharedPreferences(
                CustomApplication.applicationContext().getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            val jsonText = Gson().toJson(value)
            editor.putString(key, jsonText)

            editor.apply()
        }

        fun storeSharedPref(key: String?, value: Any?) {
            val sharedPreferences = CustomApplication.applicationContext().getSharedPreferences(
                CustomApplication.applicationContext().getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            if (value is String)
                editor.putString(key, value)
            else if (value is Boolean)
                editor.putBoolean(key, value)
            editor.apply()
        }

        fun getListSharedPref(key: String): List<String?>? {
            val sharedPreferences = CustomApplication.applicationContext().getSharedPreferences(
                CustomApplication.applicationContext().getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            //Retrieve the values
            val jsonText: String? = sharedPreferences.getString(key, null)
            if (jsonText.isNullOrBlank())
                return null
            val data = Gson().fromJson(
                jsonText,
                Array<String?>::class.java
            )

            return data?.toMutableList()
        }


        fun getSharedPref(key: String?): String? {
            val sharedPreferences = CustomApplication.applicationContext().getSharedPreferences(
                CustomApplication.applicationContext().getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getString(
                key,
                null
            )
        }

        fun bookmarkAlreadyStored(prefBookmark: String?): Boolean {
            var bookmarkData =
                getListSharedPref(Constants.PREF_BOOKMARK) as ArrayList<String?>?

            if (bookmarkData == null)
                bookmarkData = ArrayList()

            return bookmarkData.contains(prefBookmark)
        }

        fun mapLocationAlreadyStored(location: String?): Boolean {
            var locationData =
                getListSharedPref(Constants.PREF_MAP_LOCATIONS) as ArrayList<String?>?

            if (locationData == null)
                locationData = ArrayList()

            return locationData.contains(location)
        }

        fun addBookmarkEntry(prefBookmark: String?): ArrayList<String?>? {
            var bookmarkData =
                getListSharedPref(Constants.PREF_BOOKMARK) as ArrayList<String?>?

            if (prefBookmark.isNullOrBlank())
                return bookmarkData

            if (bookmarkData == null)
                bookmarkData = ArrayList()

            if (!bookmarkData.contains(prefBookmark))
                bookmarkData.add(prefBookmark)

            storeListSharedPref(Constants.PREF_BOOKMARK, bookmarkData)

            return bookmarkData

        }

        fun removeBookmarkEntry(prefBookmark: String?): ArrayList<String?>? {
            val bookmarkData =
                getListSharedPref(
                    Constants.PREF_BOOKMARK
                ) as ArrayList<String?>?
            bookmarkData?.remove(prefBookmark)

            storeListSharedPref(Constants.PREF_BOOKMARK, bookmarkData)

            return bookmarkData

        }

        fun addMapLocationEntry(mapLocation: String?): ArrayList<String?>? {
            var mapLocationData =
                getListSharedPref(Constants.PREF_MAP_LOCATIONS) as ArrayList<String?>?

            if (mapLocation.isNullOrBlank())
                return mapLocationData
            if (mapLocationData == null)
                mapLocationData = ArrayList()
            if (!mapLocationData.contains(mapLocation))
                mapLocationData.add(mapLocation)

            storeListSharedPref(Constants.PREF_MAP_LOCATIONS, mapLocationData)

            return mapLocationData

        }

        fun removeMapLocationEntry(mapLocation: String?): ArrayList<String?>? {
            val mapLocationData =
                getListSharedPref(
                    Constants.PREF_MAP_LOCATIONS
                ) as ArrayList<String?>?
            mapLocationData?.remove(mapLocation)

            storeListSharedPref(Constants.PREF_MAP_LOCATIONS, mapLocationData)

            return mapLocationData

        }
    }
}