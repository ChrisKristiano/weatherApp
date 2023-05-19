package com.example.weatherapp.presentation.main

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.manager.PermissionManager
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MainViewModel @Inject constructor(
    private val permissionManager: PermissionManager,
    private val locationManager: LocationManager,
    private val getWeather: GetWeatherUseCase
) : ViewModel(), LocationListener {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _locationError = MutableStateFlow(false)
    val locationError = _locationError.asStateFlow()

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    init { load() }

    override fun onLocationChanged(location: Location) {
        performDataUpdate(location)
    }

    fun load() {
        viewModelScope.launch { _isLoading.emit(value = true) }
        when (permissionManager.isLocationPermissionGranted()) {
            true -> locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
            false -> showError()
        }
    }

    fun getHourlyById(id: Int): Hourly? = _weather.value?.hourly?.find { it.id == id }

    fun getDailyById(id: Int): Daily? = _weather.value?.daily?.find { it.id == id }

    private fun performDataUpdate(location: Location) {
        viewModelScope.launch {
            _weather.emit(getWeather(location.latitude, location.longitude))
            _locationError.emit(value = false)
            _isLoading.emit(value = false)
        }
    }

    private fun showError() {
        viewModelScope.launch {
            _locationError.emit(value = true)
            _isLoading.emit(value = false)
        }
    }
}