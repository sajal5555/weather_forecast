package com.assignment.models.responsemodel

data class WeatherDataModel(
    var weather: ArrayList<Weather>? = null,
    var base: String? = null,
    var visibility: Int? = null,
    var dt: Int = 0,
    var timezone: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var cod: Int? = null,
    var main: Main? = null,
    var coord: Coord? = null,
    var wind: Wind? = null,
    var pop: Double? = null,
    var clouds: Clouds? = null,
    var sys: Sys? = null
)

data class Main(
    var temp: Double? = null,
    var feels_like: Double? = null,
    var temp_min: Double? = null,
    var temp_max: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null,
)

data class Weather(
    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)

data class Coord(
    var lon: Int? = null,
    var lat: Int? = null
)

data class Wind(
    var speed: Double? = null,
    var deg: Double? = null,
    var gust: Double? = null
)

data class Sys(
    var type: Int? = null,
    var id: Int? = null,
    var sunrise: Int? = null,
    var sunset: Int? = null
)

data class Clouds(var all: Int? = null)
