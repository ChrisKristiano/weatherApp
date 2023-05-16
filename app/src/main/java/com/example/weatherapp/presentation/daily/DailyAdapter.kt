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
    private val dailyList: List<Daily>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    inner class DailyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val currentDate = LocalDate.now()
        private val binding = ItemDailyBinding.bind(itemView)

        fun bind(data: Daily) {
            val isToday = currentDate.isEqual(data.time)
            val days = itemView.resources.getStringArray(R.array.date_week_day_short)

            binding.day.text = when {
                isToday -> itemView.resources.getString(R.string.date_today)
                else -> data.time?.dayOfWeek?.value?.let { days[it - 1] } ?: itemView.resources.getString(R.string.nothing)
            }
            binding.statusIcon.setImageResource(WeatherCodeTranslator.toIconDrawableRes(data.weatherCode, true))
            binding.temperatureMax.text = itemView.resources.getString(R.string.temperature, data.temperatureMax.toString())
            binding.temperatureMin.text = itemView.resources.getString(R.string.temperature, data.temperatureMin.toString())
            binding.card.setOnClickListener { onItemClick(data.id ?: -1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.DailyViewHolder =
        DailyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false))

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(dailyList[position])
    }

    override fun getItemCount(): Int = dailyList.size
}