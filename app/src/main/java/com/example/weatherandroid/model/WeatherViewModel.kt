package com.example.weatherandroid.model

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherandroid.network.WeatherApi
import com.example.weatherandroid.network.WeatherDetails
import kotlinx.coroutines.launch

enum class WeatherApiStatus {ERROR, LOADING, DONE}
enum class IconType {SUNNY, RAINY, CLOUDY, NA}

class WeatherViewModel: ViewModel() {

    private val _weatherData = MutableLiveData<WeatherDetails>()
    val weatherData: LiveData<WeatherDetails>
        get() = _weatherData

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _iconType = MutableLiveData<IconType>()
    val iconType: LiveData<IconType> = _iconType

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                _status.value = WeatherApiStatus.DONE
                _weatherData.value = WeatherApi.retrofitService.getWeatherData()
                _iconType.value = when (weatherData.value?.weather?.firstOrNull()?.id) {
                    in 200..232 -> IconType.CLOUDY
                    in 300..321 -> IconType.CLOUDY
                    in 500..531 -> IconType.CLOUDY
                    in 600..622 -> IconType.CLOUDY
                    in 701..781 -> IconType.CLOUDY
                    800 -> IconType.CLOUDY
                    in 801..804 -> IconType.CLOUDY
                    else -> IconType.NA
                }
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
            }
        }
    }
}