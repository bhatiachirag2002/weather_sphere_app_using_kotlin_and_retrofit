package com.example.weathersphere.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weathersphere.R
import com.example.weathersphere.api.ApiInterface
import com.example.weathersphere.api.ApiUtilities
import com.example.weathersphere.repo.AppRepo
import com.example.weathersphere.viewmodel.AppViewModel
import com.example.weathersphere.viewmodel.AppViewModelFactory

/**
 * MainActivity serves as the entry point of the WeatherSphere application.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        // Setup ViewModel
        setupViewModel()
    }

    /**
     * Initializes the ViewModel with the required dependencies.
     */
    private fun setupViewModel() {
        // Create the API interface
        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)

        // Create the repository with the API interface
        val appRepo = AppRepo(apiInterface)

        // Create the ViewModel factory with the repository
        val viewModelFactory = AppViewModelFactory(appRepo)

        // Initialize the ViewModel using the factory
        appViewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]
    }
}
