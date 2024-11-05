package com.example.weathersphere.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathersphere.databinding.FragmentWeatherBinding
import com.example.weathersphere.model.Daily
import com.example.weathersphere.model.Hourly
import com.example.weathersphere.ui.adapter.DailyWeatherAdapter
import com.example.weathersphere.ui.adapter.HourlyWeatherAdapter
import com.example.weathersphere.util.WeatherIconMapper
import com.example.weathersphere.viewmodel.AppViewModel
import java.util.Date
import java.util.Locale

/**
 * WeatherFragment displays the current weather and forecasts using RecyclerViews.
 */
@Suppress("DEPRECATION")
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Make the status bar transparent
        requireActivity().window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        // Ensure that the content is adjusted for insets (like status bar)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        // Fetch and display weather data
        weatherData()
    }

    @SuppressLint("SetTextI18n")
    private fun weatherData() {
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Get locality and admin area from arguments
        val locality = arguments?.getString("locality") ?: "Unknown Location"
        val adminArea = arguments?.getString("adminArea") ?: "Unknown Area"

        viewModel.weather.observe(viewLifecycleOwner) { weatherData ->
            weatherData?.let {
                // Update UI elements with weather data
                binding.addressTxtView.text = "$locality, $adminArea"
                binding.dayAndDateTxtView.text = convertUnixToDayAndDate(it.current.dt)
                setWeatherIcon(it.current.weather[0].icon)
                binding.currentWeatherTempTxtView.text = "${it.current.temp.toInt()}°C"
                binding.currentWeatherDescTxtView.text = it.current.weather[0].description
                binding.sunriseTimeTxtView.text = convertUnixToTime(it.current.sunrise)
                binding.sunsetTimeTxtView.text = convertUnixToTime(it.current.sunset)
                binding.cloudsPercentageTxtView.text = "${it.current.clouds}%"
                binding.windSpeedTxtView.text = "${it.current.wind_speed} m/s"
                binding.humidityPercentageTxtView.text = "${it.current.humidity}%"
                binding.UvIndexTxtView.text = "${it.current.uvi}"
                binding.feelsLikeTempTxtView.text = "${it.current.feels_like.toInt()}°C"
                binding.pressureTxtView.text = "${it.daily[0].rain}%"

                // Setup adapters for hourly and daily weather data
                setupWeatherAdapters(it.hourly, it.daily.subList(1, it.daily.size))
            }
        }
    }

    private fun setupWeatherAdapters(hourlyWeatherList: List<Hourly>, dailyWeatherList: List<Daily>) {
        // Set up the hourly weather RecyclerView
        binding.hourlyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyRecyclerView.adapter = HourlyWeatherAdapter(hourlyWeatherList)

        // Set up the daily weather RecyclerView
        binding.dailyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.dailyRecyclerView.adapter = DailyWeatherAdapter(dailyWeatherList)
    }

    // Convert Unix time to human-readable format (day and date)
    private fun convertUnixToDayAndDate(time: Int): String {
        val date = Date(time * 1000L)
        val format = java.text.SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        return format.format(date)
    }

    // Convert Unix time to human-readable format (time)
    private fun convertUnixToTime(time: Int): String {
        val date = Date(time * 1000L)
        val format = java.text.SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    // Set the weather icon based on the icon code
    private fun setWeatherIcon(iconCode: String) {
        val iconRes = WeatherIconMapper.getWeatherIconResource(iconCode)
        binding.currentWeatherIconsImgView.setImageResource(iconRes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Revert the status bar to default when leaving the fragment
        requireActivity().window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}
