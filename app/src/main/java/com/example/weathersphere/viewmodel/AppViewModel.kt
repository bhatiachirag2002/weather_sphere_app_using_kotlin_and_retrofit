package com.example.weathersphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersphere.model.WeatherData
import com.example.weathersphere.repo.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing weather data and business logic for the WeatherSphere application.
 *
 * @property appRepo Repository for accessing weather data.
 */
class AppViewModel(private val appRepo: AppRepo) : ViewModel() {

    /**
     * LiveData object holding the weather data.
     */
    val weather: LiveData<WeatherData>
        get() = appRepo.weather

    /**
     * Fetches the weather data for the specified latitude and longitude.
     *
     * This function launches a coroutine to fetch weather data from the repository
     * on the IO dispatcher to ensure that network operations do not block the main thread.
     *
     * @param lat The latitude of the location for which to fetch weather data.
     * @param lon The longitude of the location for which to fetch weather data.
     */
    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepo.getWeather(lat, lon)
        }
    }
}
