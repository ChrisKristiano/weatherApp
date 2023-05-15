package com.example.weatherapp.domain.model

import java.time.LocalDateTime

data class CurrentWeather(
    val temperature: Int? = null,
    val windSpeed: Double? = null,
    val windDirection: Int? = null,
    val weatherCode: WeatherCodeType? = null,
    val isDay: Boolean? = null,
    val time: LocalDateTime? = null
)
