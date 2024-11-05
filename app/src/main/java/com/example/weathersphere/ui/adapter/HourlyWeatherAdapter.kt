package com.example.weathersphere.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersphere.databinding.HourlyWeatherLayoutBinding
import com.example.weathersphere.model.Hourly
import com.example.weathersphere.util.WeatherBackgroundMapper
import com.example.weathersphere.util.WeatherIconMapper
import java.util.Date
import java.util.Locale

/**
 * Adapter class for displaying hourly weather data in a RecyclerView.
 *
 * @property hourlyWeatherList A list of Hourly weather data to be displayed in the RecyclerView.
 */
class HourlyWeatherAdapter(private val hourlyWeatherList: List<Hourly>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    /**
     * ViewHolder class for holding the view elements for each hourly weather item.
     *
     * @property itemBinding The binding object for the hourly weather layout.
     */
    class HourlyWeatherViewHolder(val itemBinding: HourlyWeatherLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    /**
     * Creates new ViewHolder instances.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *              an adapter position.
     * @param viewType The view type of the new View.
     * @return A new HourlyWeatherViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        return HourlyWeatherViewHolder(
            HourlyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the hourly weather list.
     */
    override fun getItemCount() = hourlyWeatherList.size

    /**
     * Binds data to the views for the specified position.
     *
     * @param holder The ViewHolder that will be updated to represent the contents of the item
     *               at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val hourlyWeather = hourlyWeatherList[position]

        // Set time text, display "Now" for the first position
        if (position == 0) {
            holder.itemBinding.hourlyTime.text = "Now"
        } else {
            holder.itemBinding.hourlyTime.text = convertUnixToTime(hourlyWeather.dt)
        }

        // Set the temperature text
        holder.itemBinding.hourlyTemp.text = "${hourlyWeather.temp.toInt()}°C"

        // Set weather icon based on weather condition
        setWeatherIcon(holder, hourlyWeather.weather[0].icon)

        // Set background based on weather condition
        setWeatherBackground(holder, hourlyWeather.weather[0].icon)
    }

    /**
     * Converts Unix time to a human-readable time format (hh:mm a).
     *
     * @param time The Unix time to be converted.
     * @return A formatted time string.
     */
    private fun convertUnixToTime(time: Int): String {
        val date = Date(time * 1000L) // Convert Unix time to milliseconds
        val format = java.text.SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Sets the weather icon based on the provided icon code.
     *
     * @param holder The ViewHolder where the icon will be set.
     * @param iconCode The icon code representing the weather condition.
     */
    private fun setWeatherIcon(holder: HourlyWeatherViewHolder, iconCode: String) {
        val iconRes = WeatherIconMapper.getWeatherIconResource(iconCode)
        holder.itemBinding.hourlyWeatherIconImageView.setImageResource(iconRes)
    }

    /**
     * Sets the background for the weather condition based on the icon code.
     *
     * @param holder The ViewHolder where the background will be set.
     * @param iconCode The icon code representing the weather condition.
     */
    private fun setWeatherBackground(holder: HourlyWeatherViewHolder, iconCode: String) {
        val backgroundRes = WeatherBackgroundMapper.getWeatherBackgroundResource(iconCode)
        holder.itemBinding.constraintBg.setBackgroundResource(backgroundRes)
    }
}
