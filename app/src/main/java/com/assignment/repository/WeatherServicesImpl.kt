package com.assignment.repository

import com.assignment.models.responsemodel.CityWeatherDetailResponseModel
import com.assignment.models.responsemodel.WeatherForecastResponseModel
import com.assignment.utilities.SharedPrefUtil
class WeatherServicesImpl : IWeatherServices {

    override suspend fun getWeatherForecast(
        data: String?
    ): RepoResult<WeatherForecastResponseModel?> {

        val response = try {
            Client.apis.getWeatherForecast(
                data,
                Constants.API_KEY,
                SharedPrefUtil.getSharedPref(Constants.PREF_UNIT_SYSTEM)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return if (response?.isSuccessful == true) {
            RepoResult.success(response.body())
        } else {
            RepoResult.error(response?.message() ?: Constants.ERROR_MSG)

        }
    }

    override suspend fun getCityWeatherData(
        cityName: String?
    ): RepoResult<CityWeatherDetailResponseModel?> {
        val response = try {
            Client.apis.getWeatherByCityName(
                cityName,
                Constants.API_KEY,
                SharedPrefUtil.getSharedPref(Constants.PREF_UNIT_SYSTEM)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return if (response?.isSuccessful == true) {
            RepoResult.success((response.body()))
        } else {
            RepoResult.error(response?.message() ?: Constants.ERROR_MSG)

        }
    }
}