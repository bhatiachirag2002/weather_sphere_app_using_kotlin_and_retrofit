# 🌦️ Weather Sphere - Real-time Weather Forecast

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Retrofit](https://img.shields.io/badge/Retrofit-3.0.0-green?style=for-the-badge)](https://square.github.io/retrofit/)
[![Firebase](https://img.shields.io/badge/Firebase-Remote--Config-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)](https://firebase.google.com/)
[![OpenWeatherMap](https://img.shields.io/badge/API-OpenWeatherMap-orange?style=for-the-badge)](https://openweathermap.org/)

**Weather Sphere** is a modern, high-performance weather application built with Kotlin. It provides users with accurate, real-time weather information and detailed forecasts using the **OpenWeatherMap One Call API 3.0**. The app features a clean, intuitive UI designed for seamless navigation and a great user experience.

---

## 📸 Screenshots

| Splash & Loading | Main Weather Screen |
| :---: | :---: |
| <img src="screenshot/splash screen and loading.jpg" height="450"> | <img src="screenshot/Screenshot_20260209_172736_Weather Sphere.jpg" height="450"> |

---

## ✨ Features

- 📍 **Location-Based Weather**: Automatically fetches weather data based on the user's current location using Google Play Services.
- 🌡️ **Real-time Updates**: Provides current temperature, humidity, wind speed, UV index, feels like temperature, and more.
- 📅 **Hourly & Daily Forecast**: Detailed 24-hour breakdown and 7-day weather outlook with visual icons.
- ⚡ **Dynamic Configuration**: Uses **Firebase Remote Config** to manage API keys securely without needing app updates.
- 📱 **Responsive Design**: Built using `sdp` and `ssp` libraries to ensure a consistent look across all screen sizes.
- 🎨 **Modern UI**: Featuring translucent status bars, clean typography, and a glassmorphism-inspired aesthetic.

---

## 🛠️ Project Structure

The project follows the **MVVM (Model-View-ViewModel)** architecture pattern for better separation of concerns and maintainability:

```text
app/src/main/java/com/example/weathersphere/
├── api/             # Retrofit interfaces and API service definitions
├── model/           # Data models for API responses (WeatherData, Daily, Hourly, etc.)
├── repo/            # Repository layer for data handling
├── ui/              # UI components
│   ├── activity/    # Main Activity
│   ├── fragment/    # Weather, Splash, and Error Fragments
│   └── adapter/     # RecyclerView adapters for forecasts
├── util/            # Utility classes (RemoteConfig, IconMapper, LocationUtil, NetworkUtil)
└── viewmodel/       # State management using ViewModel and LiveData
```

---

## ⚙️ Installation & Setup

### Prerequisites
- Android Studio Jellyfish or newer
- Kotlin 2.2.10+
- An OpenWeatherMap API Key (One Call API 3.0)

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/bhatiachirag2002/weather_sphere_app_using_kotlin_and_retrofit.git
   cd weather_sphere_app_using_kotlin_and_retrofit
   ```

2. **Firebase Setup**
   - Create a project on the [Firebase Console](https://console.firebase.google.com/).
   - Add an Android app (`com.example.weathersphere`) and download `google-services.json`.
   - Place `google-services.json` in the `app/` directory.
   - Enable **Remote Config** and add a parameter `api_key` with your OpenWeatherMap API key.

3. **Build the Project**
   - Open the project in Android Studio.
   - Wait for Gradle sync to complete.
   - Run the app on an emulator or a physical device.

---

## 🔌 API Details

This project utilizes the **OpenWeatherMap One Call API 3.0**.

**Request Flow:**
1. App initializes **Firebase Remote Config** via `RemoteConfigManager` to fetch the `api_key`.
2. `SplashFragment` acquires the user's location coordinates using `FusedLocationProviderClient`.
3. `AppViewModel` calls the repository, which uses `ApiInterface` to fetch weather data.
4. UI updates reactively using **LiveData** observers in `WeatherFragment`.

---

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

---

## 👨💻 Developed By
**Chirag Bhatia**  
Feel free to reach out for collaborations or feedback!

---
*If you like this project, give it a ⭐ on GitHub!*
