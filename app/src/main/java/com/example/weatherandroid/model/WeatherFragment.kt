package com.example.weatherandroid.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherandroid.R
import com.example.weatherandroid.databinding.WeatherFragmentBinding

class WeatherFragment: Fragment() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WeatherFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.weatherViewModel = viewModel
        return binding.root
    }
}