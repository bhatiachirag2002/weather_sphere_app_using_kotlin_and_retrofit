package com.example.weathersphere.model

/**
 * Data class representing the hourly weather forecast.
 *
 * @property dt Unix timestamp for the forecasted hour.
 * @property temp Temperature at the forecasted hour.
 * @property weather List of Weather objects providing detailed weather descriptions for the hour.
 */
data class Hourly(
    val dt: Int,              // Unix time of the forecasted hour
    val temp: Double,         // Temperature at the given hour
    val weather: List<Weather> // Weather details (list of Weather objects)
)
