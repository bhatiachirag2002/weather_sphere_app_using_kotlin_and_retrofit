package com.example.weathersphere.model

/**
 * Data class representing temperature details for a given day.
 *
 * @property max The maximum temperature for the day.
 * @property min The minimum temperature for the day.
 */
data class Temp(
    val max: Double,  // Maximum temperature of the day
    val min: Double   // Minimum temperature of the day
)
