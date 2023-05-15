package com.example.weatherapp.presentation.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemDailyBinding
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.presentation.common.WeatherCodeTranslator
import java.time.LocalDate

class DailyAdapter(
    private val dailyList: List<Daily>
) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    inner class DailyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val currentDate = LocalDate.now()

        fun bind(result: Daily) {
            val binding = ItemDailyBinding.bind(itemView)

            val isToday = currentDate.isEqual(result.time)
            val days = itemView.resources.getStringArray(R.array.date_week_day_short)

            binding.day.text = when {
                isToday -> itemView.resources.getString(R.string.date_today)
                else -> result.time?.dayOfWeek?.value?.let { days[it - 1] } ?: itemView.resources.getString(R.string.nothing)
            }
            binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(result.weatherCode, true))
            binding.temperatureMax.text = itemView.resources.getString(R.string.temperature, result.temperatureMax.toString())
            binding.temperatureMin.text = itemView.resources.getString(R.string.temperature, result.temperatureMin.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.DailyViewHolder =
        DailyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false))

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(dailyList[position])
    }

    override fun getItemCount(): Int = dailyList.size
}