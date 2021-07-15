package com.assignment.models.responsemodel

data class WeatherForecastResponseModel(
    var cod: String? = null,
    var message: Int? = null,
    var cnt: Int? = null,
    var list: ArrayList<WeatherDataModel>? = null,
    var city: CityDataModel? = null
)