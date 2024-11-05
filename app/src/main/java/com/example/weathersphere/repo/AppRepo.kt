package com.example.weathersphere.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathersphere.api.ApiInterface
import com.example.weathersphere.model.WeatherData

/**
 * Repository class responsible for handling API requests and providing weather data.
 *
 * @param apiInterface The API interface for fetching weather data.
 */
class AppRepo(private val apiInterface: ApiInterface) {

    // MutableLiveData to hold the weather data
    private val weatherLiveData = MutableLiveData<WeatherData>()

    // Publicly accessible LiveData to expose weather data
    val weather: LiveData<WeatherData>
        get() = weatherLiveData

    /**
     * Suspended function to fetch weather data from the API based on latitude and longitude.
     * If the API response is successful, the weather data is posted to LiveData.
     *
     * @param lat Latitude of the location for which to fetch weather data.
     * @param lon Longitude of the location for which to fetch weather data.
     */
    suspend fun getWeather(lat: Double, lon: Double) {
        // Make the API call to fetch weather data
        val result = apiInterface.getWeatherData(lat = lat, lon = lon)

        // If the response body is not null, post the value to LiveData
        if (result.body() != null) {
            weatherLiveData.postValue(result.body())
        }
    }
}
