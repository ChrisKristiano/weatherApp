package com.example.weatherapp.presentation.main

import android.annotation.SuppressLint
import android.location.Location
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.domain.manager.NetworkManager
import com.example.weatherapp.domain.manager.PermissionManager
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import com.example.weatherapp.presentation.common.ErrorState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MainViewModel @Inject constructor(
    private val permissionManager: PermissionManager,
    private val networkManager: NetworkManager,
    private val locationClient: FusedLocationProviderClient,
    private val getWeather: GetWeatherUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow(ErrorState())
    val error = _error.asStateFlow()

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    fun load() {
        viewModelScope.launch { _isLoading.emit(value = true) }
        when (networkManager.isConnected()) {
            true -> requestLocation()
            false -> showError(R.string.error_no_internet_header, R.string.error_no_internet_text)
        }
    }

    fun getHourlyById(id: Int): Hourly? = _weather.value?.hourly?.find { it.id == id }

    fun getDailyById(id: Int): Daily? = _weather.value?.daily?.find { it.id == id }

    private fun requestLocation() {
        when (permissionManager.isLocationPermissionGranted()) {
            true -> retrieveLocation()
            false -> showError(
                R.string.error_no_location_header,
                R.string.error_no_location_access_text,
                true
            )
        }
    }

    private fun retrieveLocation() {
        locationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener {
                it?.let { performDataUpdate(it) } ?: showError(
                    R.string.error_no_location_header,
                    R.string.error_no_location_off_text
                )
            }
    }

    private fun performDataUpdate(location: Location) {
        viewModelScope.launch {
            _weather.emit(getWeather(location.latitude, location.longitude))
            _error.emit(ErrorState())
            _isLoading.emit(value = false)
        }
    }

    private fun showError(
        @StringRes errorHeader: Int,
        @StringRes errorText: Int,
        doShowPermissionButton: Boolean = false
    ) {
        viewModelScope.launch {
            _error.emit(
                ErrorState(
                    isError = true,
                    doShowPermissionButton = doShowPermissionButton,
                    messageTitle = errorHeader,
                    message = errorText
                )
            )
            _isLoading.emit(value = false)
        }
    }
}