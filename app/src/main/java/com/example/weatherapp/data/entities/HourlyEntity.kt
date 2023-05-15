package com.example.weatherapp.data.entities

import com.example.weatherapp.domain.extensions.toLocalDateTime
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.WeatherCodeType

data class HourlyEntity(
    val time: List<String> = emptyList(),
    val temperature_2m: List<String> = emptyList(),
    val weathercode: List<Int> = emptyList(),
)

fun HourlyEntity.toHourlyList(): List<Hourly> = List(this.time.size) { index ->
    Hourly(
        time = this.time[index].toLocalDateTime(),
        temperature = this.temperature_2m[index],
        weatherCode = WeatherCodeType.fromCode(this.weathercode[index])
    )
}
