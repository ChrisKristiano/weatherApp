package com.example.weatherapp.presentation.hourly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemHourlyBinding
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import java.time.LocalDateTime

class HourlyAdapter(
    private val hourlyList: List<Hourly>
) : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    inner class HourlyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val currentDate = LocalDateTime.now()

        fun bind(result: Hourly) {
            val binding = ItemHourlyBinding.bind(itemView)

            val isToday = currentDate.toLocalDate().isEqual(result.time?.toLocalDate())
            val days = itemView.resources.getStringArray(R.array.date_week_day_short)

            binding.day.text = when {
                isToday -> itemView.resources.getString(R.string.date_today)
                else -> result.time?.dayOfWeek?.value?.let { days[it - 1] } ?: itemView.resources.getString(R.string.nothing)
            }
            binding.time.text = when {
                currentDate.isEqual(result.time) -> itemView.resources.getString(R.string.date_now)
                else -> result.time?.toLocalTime().toString()
            }
            binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(result.weatherCode, result.isDay))
            binding.temperature.text = itemView.resources.getString(R.string.temperature, result.temperature.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.HourlyViewHolder =
        HourlyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false))

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(hourlyList[position])
    }

    override fun getItemCount(): Int = hourlyList.size
}