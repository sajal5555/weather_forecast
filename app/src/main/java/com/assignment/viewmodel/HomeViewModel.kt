package com.assignment.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.assignment.models.responsemodel.CityWeatherDetailResponseModel
import com.assignment.models.constants.Constants
import com.assignment.repository.RepoResult
import com.assignment.repository.WeatherServicesImpl
import com.assignment.utilities.HandleOnceLiveEvent
import com.assignment.utilities.observeOnce
import kotlinx.coroutines.Dispatchers

class HomeViewModel : BaseViewModel() {
    var currentLocation = MutableLiveData<Location?>()
    val location = MutableLiveData<String?>()
    val cityWeatherDetailLiveData: MutableLiveData<CityWeatherDetailResponseModel> by lazy {
        MutableLiveData<CityWeatherDetailResponseModel>()
    }

    fun getCityWeather(postalCode: String?) {
        getWeatherInfo(postalCode)
    }

    private fun getWeatherInfo(postalCode: String?) {
        liveData(Dispatchers.IO) {
            emit(WeatherServicesImpl().getCityWeatherData(postalCode))

        }.observeOnce { repoResult ->
            when (repoResult?.status) {
                RepoResult.Status.SUCCESS -> {
                    cityWeatherDetailLiveData.postValue(repoResult.data)

                    setIsLoading(false)
                    setIsRefreshing(false)
                }
                RepoResult.Status.ERROR -> {
                    alertObservable().value =
                        HandleOnceLiveEvent(repoResult.message ?: Constants.ERROR_MSG)
                    setIsLoading(false)
                    setIsRefreshing(false)
                }
            }
        }
    }
}