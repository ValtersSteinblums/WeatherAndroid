package com.example.weatherandroid.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.openweathermap.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("data/2.5/weather?lat=57.38944&lon=21.56056&appid=e04ea9586936265be7e8a6dbdd410773&units=metric")
    suspend fun getWeatherData(): WeatherDetails

    @GET("data/2.5/forecast?q={city}&appid=e04ea9586936265be7e8a6dbdd410773&units=metric&cnt=1")
    suspend fun getSearchedWeatherData(@Path("city") city: String): WeatherSearchedDetails
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}