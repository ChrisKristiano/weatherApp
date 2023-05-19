package com.example.weatherapp.android

import android.location.Geocoder
import com.example.weatherapp.domain.manager.GeoLocationManager

class GeoLocationManagerImpl(
    private val geocoder: Geocoder
) : GeoLocationManager {

    override fun getCityName(latitude: Double, longitude: Double): String {
        val location = geocoder.getFromLocation(latitude, longitude, 1)
        return location?.first()?.locality ?: location?.first()?.adminArea.orEmpty()
    }
}