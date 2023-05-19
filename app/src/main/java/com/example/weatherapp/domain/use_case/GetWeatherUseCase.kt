package com.example.weatherapp.domain.use_case

import com.example.weatherapp.domain.manager.GeoLocationManager
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val geoLocationManager: GeoLocationManager
) {

    suspend operator fun invoke(latitude: Double, longitude: Double): Weather = coroutineScope {
        val cityName = async { geoLocationManager.getCityName(latitude, longitude) }
        val weather = async { weatherRepo.getWeather(latitude.toFloat(), longitude.toFloat()) }

        return@coroutineScope weather.await().copy(location = cityName.await())
    }
}