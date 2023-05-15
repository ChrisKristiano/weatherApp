package com.example.weatherapp.data.entities

import com.example.weatherapp.domain.model.Weather

data class WeatherEntity(
    val latitude: String? = null,
    val longitude: String? = null,
    val generationtime_ms: String? = null,
    val utc_offset_seconds: String? = null,
    val timezone: String? = null,
    val timezone_abbreviation: String? = null,
    val elevation: String? = null,
    val current_weather: CurrentWeatherEntity? = null,
    val hourly: HourlyEntity? = null,
    val daily: DailyEntity? = null
)

fun WeatherEntity.toWeather(): Weather = Weather(
    latitude = this.latitude,
    longitude = this.longitude,
    generationTimeMs = this.generationtime_ms,
    utcOffsetSeconds = this.utc_offset_seconds,
    timezoneAbbreviation = this.timezone_abbreviation,
    elevation = this.elevation,
    currentWeather = this.current_weather?.toCurrentWeather(),
    hourly = this.hourly?.toHourlyList() ?: emptyList(),
    daily = this.daily?.toDailyList() ?: emptyList(),
    location = this.timezone?.split("/")?.let { it[1] }.orEmpty()
)
