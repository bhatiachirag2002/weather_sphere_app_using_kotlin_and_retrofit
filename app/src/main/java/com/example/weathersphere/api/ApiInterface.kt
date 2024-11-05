package com.example.weathersphere.api

import com.example.weathersphere.model.WeatherData
import com.example.weathersphere.util.ApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ApiInterface provides methods to interact with the weather API.
 * It defines HTTP requests and the expected parameters for fetching weather data.
 */
interface ApiInterface {

    /**
     * Fetches weather data for a specific location using latitude and longitude.
     * This function uses Retrofit's @GET annotation to make a GET request to the weather API.
     *
     * @param lat The latitude of the location to fetch the weather for.
     * @param lon The longitude of the location to fetch the weather for.
     * @param exclude A string of data blocks to exclude from the API response. Default is "minutely".
     * @param appId The API key for authentication. Default is obtained from ApiKey.WEATHER_API_KEY.
     * @param units The unit system for the weather data (e.g., "metric" for Celsius). Default is "metric".
     * @return A Response object containing WeatherData if the request is successful.
     */
    @GET("/data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appId: String = ApiKey.WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherData>
}
