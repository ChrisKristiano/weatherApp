package com.example.weatherapp.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Daily(
    val id: Int? = null,
    val time: LocalDate? = null,
    val weatherCode: WeatherCodeType? = null,
    val temperatureMin: Int? = null,
    val temperatureMax: Int? = null,
    val apparentTemperatureMin: Int? = null,
    val apparentTemperatureMax: Int? = null,
    val sunrise: LocalDateTime? = null,
    val sunset: LocalDateTime? = null,
    val precipitationProbability: Int? = null,
    val windSpeed: Double? = null
)
