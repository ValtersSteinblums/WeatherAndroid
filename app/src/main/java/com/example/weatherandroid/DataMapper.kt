package com.example.weatherandroid

import com.example.weatherandroid.network.WeatherDetails
import com.example.weatherandroid.network.WeatherLocationDetails
import com.example.weatherandroid.network.WeatherSearchedDetails

fun mapWeatherLocationDetails(weatherLocationDetails: WeatherLocationDetails): WeatherDetails {
    return WeatherDetails(
        description = weatherLocationDetails.weather[0].description,
        city = weatherLocationDetails.name,
        currentTemp = weatherLocationDetails.main.temp,
        feelsLikeTemp = weatherLocationDetails.main.feelsLike,
        maxTemp = weatherLocationDetails.main.tempMax,
        minTemp = weatherLocationDetails.main.tempMin,
        weatherIcon = weatherLocationDetails.weather[0].id,
        humidity = weatherLocationDetails.main.humidity,
        windSpeed = weatherLocationDetails.wind.speed,
        sunrise = weatherLocationDetails.sys.sunrise,
        sunset = weatherLocationDetails.sys.sunset
    )
}

fun mapWeatherSearchedDetails(weatherSearchedDetails: WeatherSearchedDetails): WeatherDetails {
    return WeatherDetails(
        description = weatherSearchedDetails.list[0].weather[0].description,
        city = weatherSearchedDetails.city.name,
        currentTemp = weatherSearchedDetails.list[0].main.temp,
        feelsLikeTemp = weatherSearchedDetails.list[0].main.feelsLike,
        maxTemp = weatherSearchedDetails.list[0].main.tempMax,
        minTemp = weatherSearchedDetails.list[0].main.tempMin,
        weatherIcon = weatherSearchedDetails.list[0].weather[0].id,
        humidity = weatherSearchedDetails.list[0].main.humidity,
        windSpeed = weatherSearchedDetails.list[0].wind.speed,
        sunrise = weatherSearchedDetails.city.sunrise,
        sunset = weatherSearchedDetails.city.sunset
    )
}