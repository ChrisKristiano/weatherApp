package com.example.weatherapp.domain.model

data class Weather(
    val latitude: String? = null,
    val longitude: String? = null,
    val generationTimeMs: String? = null,
    val utcOffsetSeconds: String? = null,
    val timezoneAbbreviation: String? = null,
    val elevation: String? = null,
    val currentWeather: CurrentWeather? = null,
    val hourly: List<Hourly> = emptyList(),
    val daily: List<Daily> = emptyList(),
    val location: String? = null
)
