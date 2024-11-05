package com.example.weathersphere.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiUtilities is an object that provides a singleton instance of Retrofit
 * configured with the base URL and a Gson converter for interacting with the OpenWeatherMap API.
 */
object ApiUtilities {

    // Base URL for the OpenWeatherMap API
    private const val BASE_URL = "https://api.openweathermap.org/"

    /**
     * Creates and returns an instance of Retrofit to be used for making API requests.
     *
     * @return A Retrofit instance configured with the base URL and Gson converter.
     */
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)  // Set the base URL for the API requests
            .addConverterFactory(GsonConverterFactory.create())  // Add a converter to handle JSON responses
            .build()  // Build and return the Retrofit instance
    }
}
