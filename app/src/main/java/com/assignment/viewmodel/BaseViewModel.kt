package com.assignment.viewmodel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.utilities.HandleOnceLiveEvent

abstract class BaseViewModel : ViewModel() {
    //Common livedata to invoke common alerts from across the app, from viewModel/view controllers
    private val appCommonAlertObservable = MutableLiveData<HandleOnceLiveEvent<String>>()
    private val mIsLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val isRefreshing: MutableLiveData<Boolean?> = MutableLiveData()
    var startActivity = MutableLiveData<Bundle>()
    private var showActionDialog = MutableLiveData<Pair<String?, String?>>()

    open fun resetErrorMessage() {}

    @MainThread
    fun alertObservable(): MutableLiveData<HandleOnceLiveEvent<String>> {
        return appCommonAlertObservable
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getIsLoading(): MutableLiveData<Boolean?> {
        return mIsLoading
    }

    fun setIsRefreshing(refreshing: Boolean) {
        isRefreshing.value = refreshing
    }


    fun showActionMenu(recordId: String?, rowItemId: String?) {
        showActionDialog.value = Pair(recordId, rowItemId)

    }

    var textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(arg0: Editable) {
        }

        override fun beforeTextChanged(
            arg0: CharSequence, arg1: Int, arg2: Int,
            arg3: Int
        ) {

        }

        override fun onTextChanged(s: CharSequence, a: Int, b: Int, c: Int) {
            resetErrorMessage()
            editTextChange(s)
        }
    }

    open fun editTextChange(s: CharSequence) {

    }

}