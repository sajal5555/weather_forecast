package com.assignment.models.responsemodel


data class CityDataModel(
    var id: Int? = null,
    var name: String? = null,
    var coord: CityDataCoord? = null,
    var country: String? = null,
    var population: Long? = null,
    var timezone: Int? = null,
    var sunrise: Long? = null,
    var sunset: Long? = null
)

data class CityDataCoord(
    var lon: Double? = null,
    var lat: Double? = null
)
