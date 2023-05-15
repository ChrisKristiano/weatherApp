package com.example.weatherapp.domain.model

import java.time.LocalDateTime

data class Hourly(
    val time: LocalDateTime? = null,
    val temperature: String? = null,
    val weatherCode: WeatherCodeType? = null
)