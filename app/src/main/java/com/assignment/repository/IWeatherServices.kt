package com.assignment.repository

import com.assignment.models.responsemodel.CityWeatherDetailResponseModel
import com.assignment.models.responsemodel.WeatherForecastResponseModel

interface IWeatherServices {
    suspend fun getWeatherForecast(
        data: String?
    ): RepoResult<WeatherForecastResponseModel?>

    suspend fun getCityWeatherData(
        cityName: String?,
    ): RepoResult<CityWeatherDetailResponseModel?>

}