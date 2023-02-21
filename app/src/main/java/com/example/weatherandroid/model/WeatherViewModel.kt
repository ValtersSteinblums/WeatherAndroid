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
            /**
             * range all values, just to see if it works and shows the icon...
             i was thinking i would set the src value for XML here, but it will not work :D :D
             Would love to figure this one out before i continue further
             the basic idea is to check what id value the main.weather.id has, to set the correct weather icon
             I'm pretty sure it is not as simple as I'm thinking here, because in viewModel you should only set and
             get values.. And in the fragment you update the ui, if I understand that correctly.
             So i'm a little bit stuck at the moment, google is of no help either :D
             */
            in 0..900 -> {
                _iconIdString.value = "@drawable/ic_cloud"
            }
        }
        return "@drawable/ic_error"
    }

}