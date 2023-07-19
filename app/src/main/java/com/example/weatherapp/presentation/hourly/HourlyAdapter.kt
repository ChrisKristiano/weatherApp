package com.example.weatherapp.presentation.hourly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemHourlyBinding
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import com.example.weatherapp.presentation.util.extensions.orDash
import java.time.LocalDateTime

class HourlyAdapter(
    private val hourlyList: List<Hourly>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    inner class HourlyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val currentDate = LocalDateTime.now()
        private val binding = ItemHourlyBinding.bind(itemView)

        fun bind(data: Hourly) {
            val isToday = currentDate.toLocalDate().isEqual(data.time?.toLocalDate())
            val days = itemView.resources.getStringArray(R.array.date_week_day_short)

            binding.day.text = when {
                isToday -> itemView.resources.getString(R.string.date_today)
                else -> data.time?.dayOfWeek?.value?.let { days[it - 1] } ?: itemView.resources.getString(R.string.nothing)
            }
            binding.time.text = when {
                currentDate.isEqual(data.time) -> itemView.resources.getString(R.string.date_now)
                else -> data.time?.toLocalTime().toString().orDash()
            }
            binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(data.weatherCode, data.isDay))
            binding.temperature.text = itemView.resources.getString(R.string.temperature, data.temperature.toString().orDash())
            binding.card.setOnClickListener { onItemClick(data.id ?: -1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.HourlyViewHolder =
        HourlyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false))

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(hourlyList[position])
    }

    override fun getItemCount(): Int = hourlyList.size
}