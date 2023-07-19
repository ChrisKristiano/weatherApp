package com.example.weatherapp.data.entities

import com.example.weatherapp.domain.extensions.toLocalDateTime
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.WeatherCodeType

data class HourlyEntity(
    val time: List<String> = emptyList(),
    val temperature_2m: List<Double?> = emptyList(),
    val weathercode: List<Int?> = emptyList(),
    val is_day: List<Int?> = emptyList(),
    val relativehumidity_2m: List<Int?> = emptyList(),
    val apparent_temperature: List<Double?> = emptyList(),
    val precipitation_probability: List<Int?> = emptyList(),
    val surface_pressure: List<Double?> = emptyList(),
    val cloudcover: List<Int?> = emptyList(),
    val visibility: List<Double?> = emptyList(),
    val windspeed_10m: List<Double?> = emptyList()
)

fun HourlyEntity.toHourlyList(): List<Hourly> = List(this.time.size) { index ->
    Hourly(
        id = index,
        time = this.time[index].toLocalDateTime(),
        temperature = this.temperature_2m[index]?.toInt(),
        weatherCode = WeatherCodeType.fromCode(this.weathercode[index]),
        isDay = this.is_day[index]?.let { it > 0 } ?: false,
        humidity = this.relativehumidity_2m[index],
        apparentTemperature = this.apparent_temperature[index]?.toInt(),
        precipitationProbability = this.precipitation_probability[index] ?: 0,
        surfacePressure = this.surface_pressure[index],
        cloudCover = this.cloudcover[index],
        visibility = this.visibility[index]?.toInt(),
        windSpeed = this.windspeed_10m[index]
    )
}
