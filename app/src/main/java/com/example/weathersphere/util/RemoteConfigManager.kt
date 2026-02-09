package com.example.weathersphere.util

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

/**
 * RemoteConfigManager is responsible for initializing and fetching values from Firebase Remote Config.
 */
object RemoteConfigManager {

    private const val TAG = "RemoteConfigManager"
    private const val WEATHER_API_KEY_NAME = "api_key"
    /**
     * Initializes Remote Config and fetches the latest values.
     * @param onComplete Callback to be executed once the config is fetched and activated.
     */
    fun fetchAndActivate(onComplete: () -> Unit) {
        val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600).build()
        remoteConfig.setConfigSettingsAsync(configSettings)


        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                    // ApiKey object update kar rahe hain
                    ApiKey.WEATHER_API_KEY = remoteConfig.getString(WEATHER_API_KEY_NAME)
                } else {
                    Log.e(TAG, "Fetch failed")
                }
                onComplete()
            }
    }
}
