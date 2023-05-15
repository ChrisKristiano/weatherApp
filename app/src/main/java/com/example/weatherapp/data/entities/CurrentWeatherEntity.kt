package com.example.weatherapp.data.entities

import com.example.weatherapp.domain.extensions.toLocalDateTime
import com.example.weatherapp.domain.model.CurrentWeather
import com.example.weatherapp.domain.model.WeatherCodeType

data class CurrentWeatherEntity(
    val temperature: Double? = null,
    val windspeed: Double? = null,
    val winddirection: Double? = null,
    val weathercode: Int? = null,
    val is_day: Int? = null,
    val time: String? = null
)

fun CurrentWeatherEntity.toCurrentWeather(): CurrentWeather = CurrentWeather(
    temperature = this.temperature?.toInt(),
    windSpeed = this.windspeed,
    windDirection = this.winddirection?.toInt(),
    weatherCode = WeatherCodeType.fromCode(this.weathercode),
    isDay = this.is_day?.let { it > 0 } ?: false,
    time = this.time?.toLocalDateTime()
)
