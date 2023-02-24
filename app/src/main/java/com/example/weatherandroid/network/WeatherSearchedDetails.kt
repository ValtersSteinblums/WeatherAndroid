package com.example.weatherandroid.network

import com.squareup.moshi.Json

data class WeatherSearchedDetails (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListElement>,
    val city: City
)

data class City (
    val id: Long,
    val name: String,
    val sunrise: Long,
    val sunset: Long
)

data class ListElement (
    val dt: Long,
    val main: MainSearched,
    val weather: List<WeatherSearched>,
    val wind: WindSearched
)

data class MainSearched (
    val temp: Double,
    @Json(name="feels_like") val feelsLike: Double,
    @Json(name="temp_min") val tempMin: Double,
    @Json(name="temp_max") val tempMax: Double,
    val humidity: Long
)

data class WeatherSearched (
    val id: Int,
    val main: String,
    val description: String
)

data class WindSearched (
    val speed: Double
)