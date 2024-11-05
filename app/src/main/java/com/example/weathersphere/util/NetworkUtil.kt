package com.example.weathersphere.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utility object for network-related operations, specifically checking internet connectivity.
 */
object NetworkUtil {

    /**
     * Checks if the device has an active internet connection.
     *
     * @param context The application context used to access system services.
     * @return Boolean indicating whether the internet is available.
     */
    fun isInternetAvailable(context: Context): Boolean {
        // Get the ConnectivityManager from the system services
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Get the active network, return false if there is none
        val network = connectivityManager.activeNetwork ?: return false

        // Get the capabilities of the active network, return false if not available
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        // Check if the active network has internet capability
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
