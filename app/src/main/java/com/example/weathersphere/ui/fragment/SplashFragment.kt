package com.example.weathersphere.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weathersphere.R
import com.example.weathersphere.api.ApiInterface
import com.example.weathersphere.api.ApiUtilities
import com.example.weathersphere.databinding.FragmentSplashBinding
import com.example.weathersphere.repo.AppRepo
import com.example.weathersphere.util.ApiKey
import com.example.weathersphere.util.LocationUtil
import com.example.weathersphere.util.NetworkUtil
import com.example.weathersphere.util.RemoteConfigManager
import com.example.weathersphere.viewmodel.AppViewModel
import com.example.weathersphere.viewmodel.AppViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationUtil: LocationUtil
    private lateinit var viewModel: AppViewModel

    private val TAG = "wetherDebugs"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val appRepo = AppRepo(apiInterface)
        val viewModelFactory = AppViewModelFactory(appRepo)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[AppViewModel::class.java]
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationUtil = LocationUtil(requireActivity(), fusedLocationClient, requestPermissionLauncher)

        // Step 1: Remote Config se Key fetch karte hain
        Log.d(TAG, "Step 1: Fetching Remote Config...")
        RemoteConfigManager.fetchAndActivate {
            Log.d(TAG, "Step 2: Key received: ${ApiKey.WEATHER_API_KEY}")
            
            Handler(Looper.getMainLooper()).postDelayed({
                checkInternetConnection()
            }, 1000)
        }
    }

    private fun checkInternetConnection() {
        if (NetworkUtil.isInternetAvailable(requireContext())) {
            Log.d(TAG, "Step 3: Internet Available. Getting Location...")
            getCurrentLocation()
        } else {
            Log.e(TAG, "Error: No Internet Connection")
            navigateToErrorFragment("No Internet", "Check your internet connection.")
            checkInternetConnectionPeriodically()
        }
    }

    private fun getCurrentLocation() {
        locationUtil.getCurrentLocation { location ->
            if (location != null) {
                Log.d(TAG, "Step 4: Location Found -> Lat: ${location.latitude}, Lon: ${location.longitude}")
                
                val address = locationUtil.getAddressFromLocation(location)
                Log.d(TAG, "Step 5: Address fetched: $address")

                val addressParts = address.split(", ")
                val locality = addressParts.getOrNull(0) ?: "N/A"
                val adminArea = addressParts.getOrNull(2) ?: "N/A"

                Log.d(TAG, "Step 6: Fetching Weather for $locality using key: ${ApiKey.WEATHER_API_KEY}")
                viewModel.fetchWeather(location.latitude, location.longitude)

                viewModel.weather.observe(viewLifecycleOwner) { weatherData ->
                    if (weatherData != null) {
                        Log.d(TAG, "Step 7: Weather Data Received for: ${weatherData.lat} , ${weatherData.lon}")
                        val bundle = Bundle().apply {
                            putString("locality", locality)
                            putString("adminArea", adminArea)
                        }
                        findNavController().navigate(R.id.action_splashFragment_to_weatherFragment, bundle)
                    } else {
                        Log.e(TAG, "Step 7 Error: Weather Data is NULL (Check API Key activation or validity)")
                    }
                }
            } else {
                Log.e(TAG, "Error: Location is NULL even after request")
                Toast.makeText(requireContext(), "Unable to get location. Please check GPS.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToErrorFragment(title: String, message: String) {
        val bundle = Bundle().apply {
            putString("title", title)
            putString("message", message)
        }
        findNavController().navigate(R.id.errorFragment, bundle)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            Log.d(TAG, "Permission Result: $isGranted")
            if (isGranted) {
                getCurrentLocation()
            } else {
                navigateToErrorFragment("Location Permission", "This app requires location access.")
                checkLocationPermissionPeriodically()
            }
        }

    private fun checkInternetConnectionPeriodically() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (NetworkUtil.isInternetAvailable(requireContext())) {
                findNavController().popBackStack()
                checkInternetConnection()
            } else {
                checkInternetConnectionPeriodically()
            }
        }, 2000)
    }

    private fun checkLocationPermissionPeriodically() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (locationUtil.hasLocationPermission()) {
                findNavController().popBackStack()
                getCurrentLocation()
            } else {
                checkLocationPermissionPeriodically()
            }
        }, 2000)
    }
}
