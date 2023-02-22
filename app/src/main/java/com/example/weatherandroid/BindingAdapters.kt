package com.example.weatherandroid

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("setWeatherIcon")
fun bindWeatherIcon(iconImgView: ImageView, iconIntId: Int?) {
    when (iconIntId) {
        in 0..900 -> {
            iconImgView.setImageResource(R.drawable.ic_cloud)
        } else -> {
            iconImgView.setImageResource(R.drawable.ic_error)
        }
    }
}