package com.mustafa.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //  v1/forecast?hourly=temperature_2m,relativehumidity_2m,windspeed_10m,pressure_msl

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")    suspend fun getWeatherData(
        @Query("latitude") lat:Double,
        @Query("longitude") long:Double
    ):WeatherDto
}