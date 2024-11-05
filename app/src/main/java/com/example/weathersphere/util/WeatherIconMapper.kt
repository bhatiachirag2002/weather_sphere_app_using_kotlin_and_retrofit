package com.example.weathersphere.util

import com.example.weathersphere.R

/**
 * Utility object for mapping weather icon codes to their corresponding drawable resources.
 */
object WeatherIconMapper {

    /**
     * Gets the drawable resource ID for the weather icon based on the provided icon code.
     *
     * @param iconCode The weather icon code as a string (e.g., "01d", "02n").
     * @return The resource ID of the corresponding weather icon drawable, or a default icon if the code is unknown.
     */
    fun getWeatherIconResource(iconCode: String): Int {
        return when (iconCode) {
            "01d" -> R.drawable.img_01d // Clear sky day
            "01n" -> R.drawable.img_01n // Clear sky night
            "02d" -> R.drawable.img_02d // Few clouds day
            "02n" -> R.drawable.img_02n // Few clouds night
            "03d" -> R.drawable.img_03d // Scattered clouds day
            "03n" -> R.drawable.img_03n // Scattered clouds night
            "04d" -> R.drawable.img_04d // Broken clouds day
            "04n" -> R.drawable.img_04n // Broken clouds night
            "09d" -> R.drawable.img_09d // Shower rain day
            "09n" -> R.drawable.img_09n // Shower rain night
            "10d" -> R.drawable.img_10d // Rain day
            "10n" -> R.drawable.img_10n // Rain night
            "11d" -> R.drawable.img_11d // Thunderstorm day
            "11n" -> R.drawable.img_11n // Thunderstorm night
            "13d" -> R.drawable.img_13d // Snow day
            "13n" -> R.drawable.img_13n // Snow night
            "50d" -> R.drawable.img_50d // Mist day
            "50n" -> R.drawable.img_50n // Mist night
            else -> 0 // Default icon if the icon code is unknown
        }
    }
}
