package com.example.weatherapp.data.entities

import com.example.weatherapp.domain.extensions.toLocalDateTime
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.WeatherCodeType

data class HourlyEntity(
    val time: List<String> = emptyList(),
    val temperature_2m: List<Double> = emptyList(),
    val weathercode: List<Int> = emptyList(),
    val is_day: List<Int?> = emptyList(),
)

fun HourlyEntity.toHourlyList(): List<Hourly> = List(this.time.size) { index ->
    Hourly(
        time = this.time[index].toLocalDateTime(),
        temperature = this.temperature_2m[index].toInt(),
        weatherCode = WeatherCodeType.fromCode(this.weathercode[index]),
        isDay = this.is_day[index]?.let { it > 0 } ?: false
    )
}
