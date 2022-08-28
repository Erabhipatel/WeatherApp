package com.app.weatherapp.utils

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.app.weatherapp.models.*
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.coroutines.resume

class LocationTracker(
    private val application: Application,
    private val locationProvider: FusedLocationProviderClient
) {


    suspend fun getLocation(): Result<Location> {

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission ||
            !isGpsEnabled
        ) {
            Result.Error(Exception("Please enable location permission"))
        }

        return suspendCancellableCoroutine { cont ->
            locationProvider.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        Result.Success(result)
                    } else {
                        Result.Error(Exception("Something went wrong"))
                    }
                    return@suspendCancellableCoroutine

                }
                addOnSuccessListener {
                    cont.resume(Result.Success(it))
                }

                addOnFailureListener {
                    cont.resume(Result.Error(it))
                }

                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}