package com.example.weatherandroid.network

import com.squareup.moshi.Json
import java.util.Date
import kotlin.math.roundToInt

data class WeatherDetails (
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long,
)

data class Clouds (
    val all: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class Main (
    val temp: Double,
    @Json(name="feels_like") val feelsLike: Double,
    @Json(name="temp_min") val tempMin: Double,
    @Json(name="temp_max") val tempMax: Double,
    val pressure: Long,
    val humidity: Long,

    // Converting Doubles & Long -> String to show in XML, is there any better way to do this?
    val tempFeelsLikeToString: String = feelsLike.roundToInt().toString(),
    val tempMinToString: String = tempMin.roundToInt().toString(),
    val tempMaxToString: String = tempMax.roundToInt().toString(),
    val humidityToString: String = humidity.toString()
)

data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Weather (
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

data class Wind (
    val speed: Double,
    val deg: Long,

    val speedToString: String = speed.toString()
)