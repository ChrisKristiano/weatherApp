package com.example.weatherapp.data

import com.example.weatherapp.data.entities.toWeather
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.Weather

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(): Weather = api.getWeather(54.700309f, 25.272391f).toWeather()
}