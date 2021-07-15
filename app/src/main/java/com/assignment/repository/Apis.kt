package com.assignment.repository

import com.assignment.models.responsemodel.CityWeatherDetailResponseModel
import com.assignment.models.responsemodel.WeatherForecastResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Sajal Jain
 * @version 1.0
 * @since 07.13.2021
 *
 *
 * Apis class is listed with All the Open Whether Map 5 day's weather forecast api end points
 */
interface Apis {

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") postalCode: String?,
        @Query("appid") APIKEY: String?,
        @Query("units") TempUnit: String?
    ): Response<WeatherForecastResponseModel?>

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String?,
        @Query("appid") APIKEY: String?,
        @Query("units") TempUnit: String?
    ): Response<CityWeatherDetailResponseModel?>
}