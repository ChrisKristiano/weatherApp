package com.example.weatherapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.Daily
import com.example.weatherapp.domain.model.Hourly
import com.example.weatherapp.domain.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _data = MutableStateFlow<Weather?>(null)
    val data = _data.asStateFlow()

    fun load() {
        viewModelScope.launch { _data.emit(repository.getWeather()) }
    }

    fun getHourlyById(id: Int): Hourly? = _data.value?.hourly?.find { it.id == id }

    fun getDailyById(id: Int): Daily? = _data.value?.daily?.find { it.id == id }
}