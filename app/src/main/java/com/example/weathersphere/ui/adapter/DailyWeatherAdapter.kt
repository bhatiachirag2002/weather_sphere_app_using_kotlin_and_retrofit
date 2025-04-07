package com.example.weathersphere.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersphere.databinding.DailyWeatherLayoutBinding
import com.example.weathersphere.model.Daily
import com.example.weathersphere.util.WeatherIconMapper
import java.util.Date
import java.util.Locale

/**
 * Adapter class for displaying daily weather data in a RecyclerView.
 *
 * @property dailyWeatherList A list of Daily weather data to be displayed in the RecyclerView.
 */
class DailyWeatherAdapter(private val dailyWeatherList: List<Daily>) :
    RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

    /**
     * ViewHolder class for holding the view elements for each daily weather item.
     *
     * @property itemBinding The binding object for the daily weather layout.
     */
    class DailyWeatherViewHolder(val itemBinding: DailyWeatherLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    /**
     * Creates new ViewHolder instances.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *              an adapter position.
     * @param viewType The view type of the new View.
     * @return A new DailyWeatherViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        return DailyWeatherViewHolder(
            DailyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the daily weather list.
     */
    override fun getItemCount() = dailyWeatherList.size

    /**
     * Binds data to the views for the specified position.
     *
     * @param holder The ViewHolder that will be updated to represent the contents of the item
     *               at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeather = dailyWeatherList[position]
        // Set day text
        when (position) {
            0 -> {
                holder.itemBinding.dayTxtView.text = "Today"
            }
            1 -> {
                holder.itemBinding.dayTxtView.text = "Tomorrow"
            }
            else -> {
                holder.itemBinding.dayTxtView.text = convertUnixToDay(dailyWeather.dt)
            }
        }

        // Set max and min temperatures
        holder.itemBinding.maxTxtView.text = "${dailyWeather.temp.max.toInt()}°C"
        holder.itemBinding.minTxtView.text = "${dailyWeather.temp.min.toInt()}°C"
        // Set weather icon
        setWeatherIcon(holder, dailyWeather.weather[0].icon)
        // Set weather description
        if(dailyWeather.weather[0].description == "scattered clouds"){
            holder.itemBinding.descriptionTxt.text = "cloudy"
        } else holder.itemBinding.descriptionTxt.text = dailyWeather.weather[0].description
    }
    /**
     * Converts Unix time to a human-readable day format (E).
     *
     * @param time The Unix time to be converted.
     * @return A formatted day string.
     */
    private fun convertUnixToDay(time: Int): String {
        val date = Date(time * 1000L) // Convert Unix time to milliseconds
        val format = java.text.SimpleDateFormat("E", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Sets the weather icon based on the provided icon code.
     *
     * @param holder The ViewHolder where the icon will be set.
     * @param iconCode The icon code representing the weather condition.
     */
    private fun setWeatherIcon(holder: DailyWeatherViewHolder, iconCode: String) {
        val iconRes = WeatherIconMapper.getWeatherIconResource(iconCode)
        holder.itemBinding.hourlyWeatherIconImageView.setImageResource(iconRes)
    }
}
