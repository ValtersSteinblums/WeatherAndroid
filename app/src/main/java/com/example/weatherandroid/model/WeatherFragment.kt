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
            binding.cityName.text = it.name
            binding.mainTemp.text = "${it.main.temp.roundToInt().toString()}°C" //should make string.xml for all my strings
            binding.weatherDescription.text = it.weather[0].description.capitalize()
            binding.feelsLike.text = it.main.tempFeelsLikeToString
            binding.tempMax.text = it.main.tempMaxToString
            binding.tempMin.text = it.main.tempMinToString
            binding.humidity.text = it.main.humidityToString
            binding.windSpeed.text = it.wind.speedToString
        }

        viewModel.sunsetTime.observe(viewLifecycleOwner) {
            binding.sunset.text = it
        }

        viewModel.sunriseTime.observe(viewLifecycleOwner) {
            binding.sunrise.text = it
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

            binding.inputCheck.text = cityToSearchFor

            viewModel.weatherSearchedData.observe(viewLifecycleOwner) {
                binding.cityName.text = it.city.name
            }

            viewModel.status.observe(viewLifecycleOwner) {
                binding.secondApiCall.text = it.toString()
            }
            return true
        }
        return false
    }


}