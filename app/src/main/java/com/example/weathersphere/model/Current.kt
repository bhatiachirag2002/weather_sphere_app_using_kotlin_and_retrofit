package com.example.weathersphere.model

/**
 * Data class representing the current weather conditions.
 *
 * @property clouds Percentage of cloudiness.
 * @property dt Unix timestamp for the current data.
 * @property feels_like Human-perceived temperature (accounting for wind chill or humidity).
 * @property humidity Humidity percentage.
 * @property pressure Atmospheric pressure in hPa (hectopascals).
 * @property sunrise Unix timestamp for the sunrise time.
 * @property sunset Unix timestamp for the sunset time.
 * @property temp Actual current temperature.
 * @property uvi UV Index (Ultra-Violet radiation level).
 * @property weather List of Weather objects providing detailed weather descriptions.
 * @property wind_speed Wind speed in meter/sec.
 */
data class Current(
    val clouds: Int,          // Cloudiness percentage
    val dt: Int,              // Unix time of the data
    val feels_like: Double,   // "Feels like" temperature
    val humidity: Int,        // Humidity percentage
    val pressure: Int,        // Atmospheric pressure in hPa
    val sunrise: Int,         // Unix time of sunrise
    val sunset: Int,          // Unix time of sunset
    val temp: Double,         // Current temperature
    val uvi: Double,          // UV Index
    val weather: List<Weather>, // Weather details (list of Weather objects)
    val wind_speed: Double    // Wind speed in m/s
)
