package com.example.weatherandroid.model

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherandroid.R
import com.example.weatherandroid.databinding.WeatherFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.roundToInt

class WeatherFragment: Fragment() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: WeatherFragmentBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding = WeatherFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentLocation()

        // for text-field input, get new api call and dismiss the keyboard
        binding.textInputEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.myLocation.setOnClickListener { getCurrentLocation() }


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

    /* MARK: - GET USERS LOCATION */
    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                // final lat and log
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    var location: Location? = it.result
                    if (location == null) {
                        Toast.makeText(requireContext(), "Null recieved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Location recieved", Toast.LENGTH_SHORT).show()
                        viewModel.getWeatherData(location.latitude.toString(), location.longitude.toString())
                    }
                }
            } else {
                // open settings here
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            // request permission
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACCESS_LOCATION)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}