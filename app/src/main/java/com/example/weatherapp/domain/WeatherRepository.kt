package com.example.weatherapp.domain

import com.example.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(): Weather
}