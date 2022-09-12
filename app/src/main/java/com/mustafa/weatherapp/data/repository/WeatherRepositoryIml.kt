package com.mustafa.weatherapp.data.repository

import com.mustafa.weatherapp.data.mappers.toWeatherInfo
import com.mustafa.weatherapp.data.remote.WeatherApi
import com.mustafa.weatherapp.domain.repository.WeatherRepository
import com.mustafa.weatherapp.domain.util.Resource
import com.mustafa.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryIml
    @Inject
    constructor(
        private  val api:WeatherApi
    ): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return  try {
                val data = api.getWeatherData(
                    lat, long
                ).toWeatherInfo()
            Resource.Success(data = data)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e.message ?:"An unkown error occurend")
        }
    }
}