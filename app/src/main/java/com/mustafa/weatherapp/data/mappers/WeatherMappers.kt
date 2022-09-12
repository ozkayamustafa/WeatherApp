package com.mustafa.weatherapp.data.mappers

import com.mustafa.weatherapp.data.remote.WeatherDataDto
import com.mustafa.weatherapp.data.remote.WeatherDto
import com.mustafa.weatherapp.domain.weather.WeatherData
import com.mustafa.weatherapp.domain.weather.WeatherInfo
import com.mustafa.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class  IndexedWeatherData(
    val index:Int,
    val weatherData: WeatherData
)


fun WeatherDataDto.toWeatherDataMap():Map<Int,List<WeatherData>>{
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humiditie = humidities[index]
        IndexedWeatherData(
             index = index,
             weatherData =  WeatherData(
                 time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                 temperatureCelsius =  temperature,
                 pressure =  pressure,
                 windSpeed = windSpeed,
                 humidity =  humiditie,
                 weatherType = WeatherType.fromWMO(weatherCode)
             )
         )
    }.groupBy {
        it.index/24
    }.mapValues {
        it.value.map {it.weatherData}
    }.also {
        println("Key: ${it.keys} ")
        println("Deger: ${it.values}")
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo{
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 20) now.hour else now.hour+1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherPerData = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}


