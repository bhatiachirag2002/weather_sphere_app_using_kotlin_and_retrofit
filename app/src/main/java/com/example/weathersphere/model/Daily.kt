package com.example.weathersphere.model

/**
 * Data class representing the daily weather forecast.
 *
 * @property clouds Percentage of cloudiness.
 * @property dt Unix timestamp for the forecasted day's data.
 * @property humidity Humidity percentage.
 * @property rain Amount of rainfall in millimeters.
 * @property sunrise Unix timestamp for the sunrise time on the given day.
 * @property sunset Unix timestamp for the sunset time on the given day.
 * @property temp Object of Temp class representing various temperature data for the day.
 * @property weather List of Weather objects providing detailed weather descriptions.
 * @property wind_speed Wind speed in meters per second.
 */
data class Daily(
    val clouds: Int,          // Cloudiness percentage
    val dt: Int,              // Unix time of the forecasted day
    val humidity: Int,        // Humidity percentage
    val rain: Double,         // Rainfall in millimeters
    val sunrise: Int,         // Unix time of sunrise
    val sunset: Int,          // Unix time of sunset
    val temp: Temp,           // Temperature details for the day (using the Temp class)
    val weather: List<Weather>, // Weather details (list of Weather objects)
    val wind_speed: Double    // Wind speed in meters per second
)
