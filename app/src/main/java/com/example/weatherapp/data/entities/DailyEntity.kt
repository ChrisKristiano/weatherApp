package com.example.weatherapp.data.entities

import com.example.weatherapp.domain.extensions.toLocalDate
import com.example.weatherapp.domain.extensions.toLocalDateTime
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.WeatherCodeType

data class DailyEntity(
    val time: List<String> = emptyList(),
    val weathercode: List<Int> = emptyList(),
    val temperature_2m_min: List<Double> = emptyList(),
    val temperature_2m_max: List<Double> = emptyList(),
    val apparent_temperature_min: List<Double> = emptyList(),
    val apparent_temperature_max: List<Double> = emptyList(),
    val sunrise: List<String> = emptyList(),
    val sunset: List<String> = emptyList(),
    val precipitation_probability_max: List<Int?> = emptyList(),
    val windspeed_10m_max: List<Double> = emptyList()
)

fun DailyEntity.toDailyList(): List<Daily> = List(this.time.size) { index ->
    Daily(
        id = index,
        time = this.time[index].toLocalDate(),
        weatherCode = WeatherCodeType.fromCode(this.weathercode[index]),
        temperatureMin = this.temperature_2m_min[index].toInt(),
        temperatureMax = this.temperature_2m_max[index].toInt(),
        apparentTemperatureMin = this.apparent_temperature_min[index].toInt(),
        apparentTemperatureMax = this.apparent_temperature_max[index].toInt(),
        sunrise = this.sunrise[index].toLocalDateTime(),
        sunset = this.sunset[index].toLocalDateTime(),
        precipitationProbability = this.precipitation_probability_max[index] ?: 0,
        windSpeed = this.windspeed_10m_max[index]
    )
}
