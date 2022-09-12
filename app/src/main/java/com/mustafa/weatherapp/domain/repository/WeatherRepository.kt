package com.mustafa.weatherapp.domain.repository

import com.mustafa.weatherapp.domain.util.Resource
import com.mustafa.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat:Double,long:Double):Resource<WeatherInfo>
}