package com.example.weatherandroid.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherandroid.network.WeatherApi
import com.example.weatherandroid.network.WeatherDetails
import com.example.weatherandroid.network.WeatherSearchedDetails
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class WeatherApiStatus {ERROR, LOADING, DONE}
enum class IconType {SUNNY, RAINY, CLOUDY, WINDY, THUNDER, SNOW, FOG, NA}

class WeatherViewModel: ViewModel() {

    private val _weatherData = MutableLiveData<WeatherDetails>()
    val weatherData: LiveData<WeatherDetails>
        get() = _weatherData

    private val _weatherSearchedData = MutableLiveData<WeatherSearchedDetails>()
    val weatherSearchedData: LiveData<WeatherSearchedDetails>
        get() = _weatherSearchedData

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _iconType = MutableLiveData<IconType>()
    val iconType: LiveData<IconType> = _iconType

    private val _sunsetTime = MutableLiveData<String>()
    val sunsetTime: LiveData<String> = _sunsetTime

    private val _sunriseTime = MutableLiveData<String>()
    val sunriseTime: LiveData<String> = _sunriseTime

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                _status.value = WeatherApiStatus.DONE
                _weatherData.value = WeatherApi.retrofitService.getWeatherData()

                // set the weather icon values based on the WeatherDetails.weather[0].id value
                _iconType.value = when (weatherData.value?.weather?.firstOrNull()?.id) {
                    in 0..300 -> IconType.WINDY
                    in 301..600 -> IconType.RAINY
                    in 601..700 -> IconType.SNOW
                    in 701..771 -> IconType.FOG
                    in 772..799 -> IconType.THUNDER
                    800 -> IconType.SUNNY
                    in 801..804 -> IconType.FOG
                    in 900..903, in 905..1000 -> IconType.THUNDER
                    903 -> IconType.SNOW
                    904 -> IconType.SUNNY
                    else -> IconType.NA
                }

                _sunsetTime.value = getReadableTimeData(weatherData.value?.sys?.sunset ?: 1677091200)
                _sunriseTime.value = getReadableTimeData(weatherData.value?.sys?.sunrise ?: 1677091200)
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
            }
        }
    }

    private fun getReadableTimeData(unixTime: Long): String {
        val date = Date(unixTime * 1000L)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(date)
    }

    fun getSearchedCityWeatherData(userSearchQuery: String) {
        viewModelScope.launch {
            try {
                _weatherSearchedData.value = WeatherApi.retrofitService.getSearchedWeatherData(userSearchQuery)

                _iconType.value = when (weatherSearchedData.value?.list?.get(2)?.weather?.get(0)?.id) {
                    in 0..300 -> IconType.WINDY
                    in 301..600 -> IconType.RAINY
                    in 601..700 -> IconType.SNOW
                    in 701..771 -> IconType.FOG
                    in 772..799 -> IconType.THUNDER
                    800 -> IconType.SUNNY
                    in 801..804 -> IconType.FOG
                    in 900..903, in 905..1000 -> IconType.THUNDER
                    903 -> IconType.SNOW
                    904 -> IconType.SUNNY
                    else -> IconType.NA
                }
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
            }
        }
    }
}