package com.example.weatherapp.data.repository

import com.example.weatherapp.data.entities.toWeather
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.model.Weather

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(
        latitude: Float,
        longitude: Float
    ): Weather = api.getWeather(latitude, longitude).toWeather()
}