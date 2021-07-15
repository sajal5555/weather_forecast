package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData

class BookmarkViewModel : BaseViewModel() {
    val cancelClickLiveData = MutableLiveData<String?>()


    fun onCancelClick(city: String?) {
        cancelClickLiveData.value = city
    }
}