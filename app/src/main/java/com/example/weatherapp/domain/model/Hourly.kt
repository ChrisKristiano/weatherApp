package com.example.weatherapp.domain.model

import java.time.LocalDateTime

data class Hourly(
    val id: Int? = null,
    val time: LocalDateTime? = null,
    val temperature: Int? = null,
    val weatherCode: WeatherCodeType? = null,
    val isDay: Boolean? = null,
    val humidity: Int? = null,
    val apparentTemperature: Int? = null,
    val precipitationProbability: Int? = null,
    val surfacePressure: Double? = null,
    val cloudCover: Int? = null,
    val visibility: Int? = null,
    val windSpeed: Double? = null
)
