package com.mustafa.weatherapp.domain.weather

data class WeatherInfo(
    val weatherPerData : Map<Int,List<WeatherData>>,
    val currentWeatherData: WeatherData?
)
