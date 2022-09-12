package com.mustafa.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.mustafa.weatherapp.domain.location.LocationTracker
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker
    @Inject constructor(
        private val locaitonClient:FusedLocationProviderClient,
        private val application: Application
    ):LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val hasAccesFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccesCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasAccesCoarseLocationPermission || !hasAccesFineLocationPermission || !isGPSEnabled){
            return null
        }

        return suspendCancellableCoroutine { cont->
                locaitonClient.lastLocation.apply {
                    if (isComplete){
                        if (isSuccessful){
                            cont.resume(result)
                        }else{
                            cont.resume(null)
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        cont.resume(it)
                    }
                    addOnFailureListener {
                        cont.resume(null)
                    }
                    addOnCanceledListener {
                        cont.cancel()
                    }
                }
        }
    }
}