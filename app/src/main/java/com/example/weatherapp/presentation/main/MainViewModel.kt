package com.example.weatherapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    fun load (isNetworkActive: Boolean) {
        if (isNetworkActive) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.emit(true)
                _isError.emit(false)
                _weather.emit(repository.getWeather())
                _isLoading.emit(false)
            }
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                _isLoading.emit(true)
                _isError.emit(true)
                _isLoading.emit(false)
            }
        }
    }

    fun getHourlyById(id: Int): Hourly? = _weather.value?.hourly?.find { it.id == id }

    fun getDailyById(id: Int): Daily? = _weather.value?.daily?.find { it.id == id }
}