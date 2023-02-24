package com.example.weatherandroid.network

import com.squareup.moshi.Json
import kotlin.math.roundToInt

data class WeatherDetails (
    val description: String,
    val city: String,

    val weatherIcon: Int,

    val humidity: Long,
    val windSpeed: Double,
    val maxTemp: Double,
    val minTemp: Double,
    val feelsLikeTemp: Double,
    val currentTemp: Double,

    val sunset: Long,
    val sunrise: Long
)