package com.example.weatherapp.domain.manager

interface GeoLocationManager {
    fun getCityName(latitude: Double, longitude: Double): String
}