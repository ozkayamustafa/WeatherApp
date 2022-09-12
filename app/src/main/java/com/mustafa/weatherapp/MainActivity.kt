package com.mustafa.weatherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mustafa.weatherapp.presentation.WeatherViewModel
import com.mustafa.weatherapp.presentation.ui.WeatherCard
import com.mustafa.weatherapp.presentation.ui.WeatherForecast
import com.mustafa.weatherapp.ui.theme.DarkBlue
import com.mustafa.weatherapp.ui.theme.DeepBlue
import com.mustafa.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewmodel:WeatherViewModel by viewModels()
    private lateinit var permissionLaouncher:ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        permissionLaouncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            viewmodel.loadWeatherInfo()
        }
        permissionLaouncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DarkBlue)
                        ) {
                            WeatherCard(state = viewmodel.state, background = DeepBlue)
                            Spacer(modifier = Modifier.height(16.dp))
                            WeatherForecast(state = viewmodel.state)

                        }
                        viewmodel.state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                            )
                        }

                    }

            }
        }
    }
}

