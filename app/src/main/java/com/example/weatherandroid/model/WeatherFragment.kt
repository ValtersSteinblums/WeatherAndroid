package com.example.weatherandroid.model

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherandroid.R
import com.example.weatherandroid.databinding.WeatherFragmentBinding
import kotlin.math.roundToInt

class WeatherFragment: Fragment() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: WeatherFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherFragmentBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }


        viewModel.weatherData.observe(viewLifecycleOwner) {
            binding.cityName.text = it.city
            binding.mainTemp.text = getString(R.string.temperature, it.currentTemp.roundToInt())
            binding.weatherDescription.text = it.description.capitalize()
            binding.feelsLike.text = getString(R.string.feels_like_temperature, it.feelsLikeTemp.roundToInt())
            binding.tempMax.text = getString(R.string.max_temperature, it.maxTemp.roundToInt())
            binding.tempMin.text = getString(R.string.min_temperature, it.minTemp.roundToInt())
            binding.humidity.text = getString(R.string.humidity, it.humidity)
            binding.windSpeed.text = getString(R.string.wind_speed, it.windSpeed.roundToInt())
        }

        viewModel.sunsetTime.observe(viewLifecycleOwner) {
            binding.sunset.text = getString(R.string.sunset, it)
        }

        viewModel.sunriseTime.observe(viewLifecycleOwner) {
            binding.sunrise.text = getString(R.string.sunrise, it)
        }

        // https://freeicons.io/icon-list/weather-icon-set-3
        viewModel.iconType.observe(viewLifecycleOwner) {
            val iconId = when (it) {
                IconType.CLOUDY -> R.drawable.clouds
                IconType.SUNNY -> R.drawable.sunny
                IconType.RAINY -> R.drawable.rain
                IconType.WINDY -> R.drawable.windy
                IconType.THUNDER -> R.drawable.thunder
                IconType.SNOW -> R.drawable.rain
                IconType.FOG -> R.drawable.fog
                IconType.NA -> R.drawable.ic_error
            }
            binding.weatherIcon.setImageResource(iconId)
        }

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val cityToSearchFor = binding.textInputEditText.text?.trim().toString()

            viewModel.getSearchedCityWeatherData(cityToSearchFor)

            // hide the keyboard
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }


}