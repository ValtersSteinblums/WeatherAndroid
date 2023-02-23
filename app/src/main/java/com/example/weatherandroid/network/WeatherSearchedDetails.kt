package com.example.weatherandroid.network

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
    val country: String,
    val population: Long,
    val timezone: Long,
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
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Long,
    val tempKf: Double
)

data class WeatherSearched (
    val id: Int,
    val main: String,
    val description: String
)

data class WindSearched (
    val speed: Double
)