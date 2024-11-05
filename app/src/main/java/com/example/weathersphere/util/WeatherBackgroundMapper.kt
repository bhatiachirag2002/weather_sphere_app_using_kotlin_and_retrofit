package com.example.weathersphere.util

import com.example.weathersphere.R

/**
 * Utility object for mapping weather icon codes to their corresponding background drawable resources.
 */
object WeatherBackgroundMapper {

    /**
     * Gets the drawable resource ID for the weather background based on the provided icon code.
     *
     * @param iconCode The weather icon code as a string (e.g., "01d", "02n").
     * @return The resource ID of the corresponding background drawable, or 0 if the icon code is unknown.
     */
    fun getWeatherBackgroundResource(iconCode: String): Int {
        return when (iconCode) {
            "01d" -> R.drawable.bg_01d // Clear sky day
            "01n" -> R.drawable.bg_01n // Clear sky night
            "02d" -> R.drawable.bg_02d // Few clouds day
            "02n" -> R.drawable.bg_02n // Few clouds night
            "03d" -> R.drawable.bg_03d // Scattered clouds day
            "03n" -> R.drawable.bg_03n // Scattered clouds night
            "04d" -> R.drawable.bg_04d // Broken clouds day
            "04n" -> R.drawable.bg_04n // Broken clouds night
            "09d" -> R.drawable.bg_09d // Shower rain day
            "09n" -> R.drawable.bg_09n // Shower rain night
            "10d" -> R.drawable.bg_10d // Rain day
            "10n" -> R.drawable.bg_10n // Rain night
            "11d" -> R.drawable.bg_11d // Thunderstorm day
            "11n" -> R.drawable.bg_11n // Thunderstorm night
            "13d" -> R.drawable.bg_13d // Snow day
            "13n" -> R.drawable.bg_13n // Snow night
            "50d" -> R.drawable.bg_50d // Mist day
            "50n" -> R.drawable.bg_50n // Mist night
            else -> 0 // Default icon if the icon code is unknown
        }
    }
}
