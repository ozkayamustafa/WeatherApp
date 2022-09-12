package com.mustafa.weatherapp.presentation

import com.mustafa.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isBoolean: Boolean = false,
    val error:String? = null
)
