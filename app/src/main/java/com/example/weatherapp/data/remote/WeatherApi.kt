package com.example.weatherapp.data.remote

import com.example.weatherapp.data.entities.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast?hourly=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,weathercode,surface_pressure,cloudcover,visibility,windspeed_10m,is_day&current_weather=true&timezone=auto&forecast_days=14&daily=weathercode,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,uv_index_max,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_hours,precipitation_probability_max,windspeed_10m_max")
    suspend fun getWeather(
        @Query(value = "latitude") latitude: Float,
        @Query(value = "longitude") longitude: Float
    ): WeatherEntity
}