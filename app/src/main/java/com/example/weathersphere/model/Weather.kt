package com.example.weathersphere.model

/**
 * Data class representing detailed weather information.
 *
 * @property description A short description of the weather conditions (e.g., "clear sky").
 * @property icon The icon code corresponding to the weather condition (used for displaying a weather icon).
 * @property id The weather condition ID (useful for categorizing conditions).
 * @property main A brief label for the weather condition (e.g., "Clear", "Clouds").
 */
data class Weather(
    val description: String,  // Short description of weather (e.g., "clear sky")
    val icon: String,         // Icon code representing the weather (used for displaying icons)
    val id: Int,              // Weather condition ID (used for categorization)
    val main: String          // Main label for the weather condition (e.g., "Clear", "Clouds")
)
