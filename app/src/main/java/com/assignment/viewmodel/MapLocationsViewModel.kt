package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData

class MapLocationsViewModel : BaseViewModel() {
    val cancelClickLiveData = MutableLiveData<String?>()
    val bookmarkClickLiveData = MutableLiveData<String?>()

    fun onBookmarkClick(city: String?) {
        bookmarkClickLiveData.value = city
    }

    fun onCancelClick(city: String?) {
        cancelClickLiveData.value = city
    }
}