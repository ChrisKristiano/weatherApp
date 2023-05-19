package com.example.weatherapp.domain.manager

interface PermissionManager {
    fun isLocationPermissionGranted(): Boolean
}