package com.mustafa.weatherapp.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.weatherapp.domain.location.LocationTracker
import com.mustafa.weatherapp.domain.repository.WeatherRepository
import com.mustafa.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private  val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
            private set


    fun loadWeatherInfo(){
        viewModelScope.launch {
            state = state.copy(
                isBoolean = false,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->  
                when(val result = repository.getWeatherData(location.latitude,location.longitude)){
                    is Resource.Success->{
                        state = state.copy(
                            weatherInfo = result.data,
                            isBoolean = false,
                            error = null
                        )
                    }
                    is Resource.Error->{
                            state = state.copy(
                                weatherInfo = null,
                                isBoolean = false,
                                error = result.message
                            )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isBoolean = false,
                    error = "Couldn't  retrieve location. Make sure to grant permission and enable GPS."
                )
            }

        }




    }


}