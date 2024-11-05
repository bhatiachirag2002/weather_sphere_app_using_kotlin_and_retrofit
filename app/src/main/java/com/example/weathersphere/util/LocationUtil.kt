package com.example.weathersphere.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*

/**
 * Utility class for managing location services such as checking permissions,
 * fetching the user's current location, and getting an address from a location.
 *
 * @param context The application context.
 * @param fusedLocationClient The client used to access location services.
 * @param requestPermissionLauncher Launcher for requesting location permissions.
 */
@Suppress("DEPRECATION")
class LocationUtil(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val requestPermissionLauncher: ActivityResultLauncher<String>
) {

    /**
     * Checks if the app has the necessary location permission (ACCESS_FINE_LOCATION).
     *
     * @return Boolean indicating if location permission is granted.
     */
    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Fetches the user's current location if location permission is granted.
     * If permission is not granted, it triggers the permission request.
     *
     * @param onLocationFetched Callback function to handle the fetched location.
     */
    fun getCurrentLocation(onLocationFetched: (Location?) -> Unit) {
        // If permission is not granted, request it
        if (!hasLocationPermission()) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Try to fetch the last known location
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    onLocationFetched(location)
                }
            } catch (e: SecurityException) {
                // Handle any security exception, return null if location cannot be fetched
                onLocationFetched(null)
            }
        }
    }

    /**
     * Converts a Location object to a human-readable address using Geocoder.
     *
     * @param location The location for which the address is fetched.
     * @return A formatted address string or "N/A" if no address is found.
     */
    fun getAddressFromLocation(location: Location): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address = geocoder.getFromLocation(
            location.latitude, location.longitude, 1
        )?.firstOrNull()

        // Return the formatted address or "N/A" if no address is available
        return if (address != null) {
            "${address.locality}, ${address.subLocality}, ${address.adminArea}"
        } else {
            "N/A"
        }
    }
}
