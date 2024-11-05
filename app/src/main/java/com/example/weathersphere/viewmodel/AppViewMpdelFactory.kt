package com.example.weathersphere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathersphere.repo.AppRepo

/**
 * Factory class for creating instances of [AppViewModel].
 *
 * This factory takes an [AppRepo] instance as a parameter to provide the [AppViewModel]
 * with the necessary data repository for fetching weather data.
 *
 * @param appRepo The repository instance used by the ViewModel.
 */
@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(private val appRepo: AppRepo) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return An instance of the specified ViewModel.
     * @throws IllegalArgumentException if the given ViewModel class is not supported.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel class is AppViewModel
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            // Create and return an instance of AppViewModel
            return AppViewModel(appRepo) as T
        }
        // Throw an exception if the ViewModel class is not recognized
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
