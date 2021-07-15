package com.assignment.interfaces

import com.assignment.models.responsemodel.CityWeatherDetailResponseModel
import com.assignment.models.responsemodel.WeatherForecastResponseModel
import com.assignment.repository.RepoResult

interface IWeatherServices {
    suspend fun getWeatherForecast(
        data: String?
    ): RepoResult<WeatherForecastResponseModel?>

    suspend fun getCityWeatherData(
        cityName: String?,
    ): RepoResult<CityWeatherDetailResponseModel?>

}