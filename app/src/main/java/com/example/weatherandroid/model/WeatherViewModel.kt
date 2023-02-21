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

class WeatherViewModel: ViewModel() {

    private val _weatherData = MutableLiveData<WeatherDetails>()
    val weatherData: LiveData<WeatherDetails> = _weatherData

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _iconIdString = MutableLiveData<String>()
    val iconIdString: LiveData<String> = _iconIdString

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                _status.value = WeatherApiStatus.DONE
                _weatherData.value = WeatherApi.retrofitService.getWeatherData()
//                _iconIdString.value = getCurrentWeatherIcon()
                getCurrentWeatherIcon(_weatherData.value!!.weather[0].id)
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
            }
        }
    }

    private fun getCurrentWeatherIcon(iconId: Int): String {
//        return _weatherData.value?.weather?.get(0)?.id.toString()
        when (iconId) {
            // range all values, just to see if it works and shows the icon...
            in 0..900 -> {
                _iconIdString.value = "@drawable/ic_cloud"
            }
        }
        return "@drawable/ic_error"
    }

}