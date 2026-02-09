package com.example.weathersphere.util

/**
 * Object to store the API key for the OpenWeatherMap API.
 * This key can be updated dynamically from Firebase Remote Config.
 */
object ApiKey {
    // Default key ya placeholder, ise Remote Config se update kiya jayega
    var WEATHER_API_KEY: String = "YOUR_FALLBACK_API_KEY"
}
