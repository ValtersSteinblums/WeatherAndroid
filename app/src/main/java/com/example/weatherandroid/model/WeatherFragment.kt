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
            binding.mainTemp.text = it.main.tempNow
        }

        viewModel.iconType.observe(viewLifecycleOwner) {
            val iconId = when (it) {
                IconType.CLOUDY -> R.drawable.ic_cloud
                IconType.SUNNY -> R.drawable.ic_cloud
                IconType.RAINY -> R.drawable.ic_cloud
                IconType.NA -> R.drawable.ic_error
            }
            binding.weatherIcon.setImageResource(iconId)
        }
    }


}