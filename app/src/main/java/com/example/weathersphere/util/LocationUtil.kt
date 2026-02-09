package com.example.weathersphere.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import java.util.*

@Suppress("DEPRECATION")
class LocationUtil(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val requestPermissionLauncher: ActivityResultLauncher<String>
) {

    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onLocationFetched: (Location?) -> Unit) {
        if (!hasLocationPermission()) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Pehle last location try karo
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    onLocationFetched(location)
                } else {
                    // Agar last location null hai, to fresh location request karo
                    requestFreshLocation(onLocationFetched)
                }
            }.addOnFailureListener {
                requestFreshLocation(onLocationFetched)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestFreshLocation(onLocationFetched: (Location?) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setMaxUpdates(1) // Sirf ek baar location chahiye
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                onLocationFetched(location)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun getAddressFromLocation(location: Location): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)?.firstOrNull()
            if (address != null) {
                "${address.locality ?: ""}, ${address.subLocality ?: ""}, ${address.adminArea ?: ""}"
            } else "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }
}
