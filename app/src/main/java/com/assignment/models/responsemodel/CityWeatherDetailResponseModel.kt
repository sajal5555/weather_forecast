package com.assignment.models.responsemodel

class CityWeatherDetailResponseModel {
    val coord: CoordBean? = null
    val weather: List<WeatherBean>? = null
    val base: String? = null
    val main: MainBean? = null
    val visibility: Int? = null
    val pop : Double? = null
    val wind: WindBean? = null
    val clouds: CloudsBean? = null
    val dt: Int? = null
    val sys: SysBean? = null
    val timezone: Int? = null
    val id: Int? = null
    val name: String? = null
    val cod: Int? = null

    class CoordBean {
        val lon: Double? = null
        val lat: Double? = null
    }

    class MainBean {
        val temp: Double? = null
        val feels_like: Double? = null
        val temp_min: Double? = null
        val temp_max: Double? = null
        val pressure: Int? = null
        val humidity: Int? = null
    }

    class WindBean {
        val speed: Double? = null
        val deg: Int? = null
    }

    class CloudsBean {
        val all: Int? = null
    }

    class SysBean {
        val type: Int? = null
        val id: Int? = null
        val country: String? = null
        val sunrise: Int? = null
        val sunset: Int? = null
    }

    class WeatherBean {
        val id: Int? = null
        val main: String? = null
        val description: String? = null
        val icon: String? = null
    }
}