package com.example.weatherandroid.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherandroid.R
import com.example.weatherandroid.databinding.WeatherFragmentBinding

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

        viewModel.weatherData.observe(viewLifecycleOwner) {
            binding.cityName.text = it.name
            binding.mainTemp.text = "${it.main.tempNow}°C" //should make string.xml for all my strings
        }

        // https://freeicons.io/icon-list/weather-icon-set-3
        viewModel.iconType.observe(viewLifecycleOwner) {
            val iconId = when (it) {
                IconType.CLOUDY -> R.drawable.clouds
                IconType.SUNNY -> R.drawable.clouds
                IconType.RAINY -> R.drawable.clouds
                IconType.NA -> R.drawable.clouds
            }
            binding.weatherIcon.setImageResource(iconId)
        }
    }


}