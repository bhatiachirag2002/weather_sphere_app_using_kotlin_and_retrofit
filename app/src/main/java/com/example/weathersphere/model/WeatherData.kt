package com.example.weathersphere.model

/**
 * Data class representing the overall weather data response from the API.
 *
 * @property current The current weather conditions.
 * @property daily A list of daily weather forecasts.
 * @property hourly A list of hourly weather forecasts.
 * @property lat Latitude of the location for which the weather data is provided.
 * @property lon Longitude of the location for which the weather data is provided.
 */
data class WeatherData(
    val current: Current,        // Current weather conditions
    val daily: List<Daily>,      // Daily weather forecast (list of Daily objects)
    val hourly: List<Hourly>,    // Hourly weather forecast (list of Hourly objects)
    val lat: Double,             // Latitude of the location
    val lon: Double              // Longitude of the location
)
