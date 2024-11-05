package com.example.weathersphere.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weathersphere.R
import com.example.weathersphere.databinding.FragmentSplashBinding
import com.example.weathersphere.util.LocationUtil
import com.example.weathersphere.util.NetworkUtil
import com.example.weathersphere.viewmodel.AppViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * SplashFragment displays a splash screen while checking for internet connectivity and location permissions.
 */
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationUtil: LocationUtil
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adjust content for insets (like status bar)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        // Initialize ViewModel and Location utilities
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationUtil = LocationUtil(requireActivity(), fusedLocationClient, requestPermissionLauncher)

        Handler(Looper.getMainLooper()).postDelayed({
            checkInternetConnection()
        }, 2000)

    }

    /**
     * Checks if the internet is available. If not, navigates to an error fragment.
     */
    private fun checkInternetConnection() {
        if (NetworkUtil.isInternetAvailable(requireContext())) {
            getCurrentLocation()
        } else {
            navigateToErrorFragment("No Internet", "Check your internet connection.")
            checkInternetConnectionPeriodically()
        }
    }

    /**
     * Retrieves the current location using LocationUtil and fetches weather data.
     */
    private fun getCurrentLocation() {
        locationUtil.getCurrentLocation { location ->
            location?.let {
                val address = locationUtil.getAddressFromLocation(it)
                val addressParts = address.split(", ")
                val locality = addressParts.getOrNull(0) ?: "N/A"
                val adminArea = addressParts.getOrNull(2) ?: "N/A"

                // Fetch weather data using ViewModel
                viewModel.fetchWeather(it.latitude, it.longitude)

                // Observe weather data and navigate to the WeatherFragment
                viewModel.weather.observe(viewLifecycleOwner) { weatherData ->
                    if (weatherData != null) {
                        val bundle = Bundle().apply {
                            putString("locality", locality)
                            putString("adminArea", adminArea)
                        }
                        findNavController().navigate(R.id.action_splashFragment_to_weatherFragment, bundle)
                    }
                }
            }
        }
    }

    /**
     * Navigates to an error fragment with the provided title and message.
     */
    private fun navigateToErrorFragment(title: String, message: String) {
        val bundle = Bundle().apply {
            putString("title", title)
            putString("message", message)
        }
        findNavController().navigate(R.id.errorFragment, bundle)
    }

    // Request permission launcher for location access
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation() // Get the current location if permission is granted
            } else {
                navigateToErrorFragment("Location Permission", "This app requires location access.")
                checkLocationPermissionPeriodically()
            }
        }

    /**
     * Periodically checks for internet connection availability.
     */
    private fun checkInternetConnectionPeriodically() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (NetworkUtil.isInternetAvailable(requireContext())) {
                findNavController().popBackStack() // Dismiss error dialog
            } else {
                checkInternetConnectionPeriodically() // Continue checking
            }
        }, 1000)
    }

    /**
     * Periodically checks if the location permission is granted.
     */
    private fun checkLocationPermissionPeriodically() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (locationUtil.hasLocationPermission()) {
                findNavController().popBackStack() // Dismiss error dialog
                getCurrentLocation() // Get current location
            } else {
                checkLocationPermissionPeriodically() // Continue checking
            }
        }, 1000)
    }
}
