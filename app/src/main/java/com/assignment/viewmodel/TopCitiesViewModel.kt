package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData

class TopCitiesViewModel : BaseViewModel() {

    val bookmarkClickLiveData = MutableLiveData<String?>()

    fun onBookmarkClick(city: String?) {
        bookmarkClickLiveData.value = city
    }
}