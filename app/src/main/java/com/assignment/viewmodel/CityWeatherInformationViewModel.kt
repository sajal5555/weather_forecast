package com.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.assignment.models.responsemodel.WeatherDataModel
import com.assignment.models.constants.Constants
import com.assignment.repository.RepoResult
import com.assignment.repository.WeatherServicesImpl
import com.assignment.utilities.HandleOnceLiveEvent
import com.assignment.utilities.DateTimeUtil
import com.assignment.utilities.observeOnce
import kotlinx.coroutines.Dispatchers
import java.util.*

class CityWeatherInformationViewModel : BaseViewModel() {
    val cityWeatherDetailLiveData: MutableLiveData<ArrayList<List<WeatherDataModel>>> by lazy {
        MutableLiveData<ArrayList<List<WeatherDataModel>>>()
    }

    fun getCityWeather(cityName: String?) {
        getWeatherInfo(cityName)
    }

    private fun getWeatherInfo(cityName: String?) {
        liveData(Dispatchers.IO) {
            emit(WeatherServicesImpl().getWeatherForecast(cityName))

        }.observeOnce { repoResult ->
            when (repoResult?.status) {
                RepoResult.Status.SUCCESS -> {
                    val weatherList = repoResult.data


                    val distinctDays = HashSet<String>()
                    weatherList?.list?.let {
                        for (obj in it) {
                            distinctDays.add(DateTimeUtil().getDay(obj.dt))
                        }

                        val days = ArrayList<String>()
                        days.addAll(distinctDays)
                        val daysList = ArrayList<List<WeatherDataModel>>()

                        for (day in days) {
                            val temp: MutableList<WeatherDataModel> = ArrayList<WeatherDataModel>()

                            for (data in it) {
                                if (DateTimeUtil().getDay(data.dt) == day) {
                                    temp.add(data)
                                }
                            }
                            daysList.add(temp)
                        }
                        cityWeatherDetailLiveData.value = daysList

                    }


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