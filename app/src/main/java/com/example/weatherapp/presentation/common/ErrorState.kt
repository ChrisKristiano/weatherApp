package com.example.weatherapp.presentation.common

import androidx.annotation.StringRes

data class ErrorState(
    val isError: Boolean = false,
    val doShowPermissionButton: Boolean = false,
    @StringRes val messageTitle: Int? = null,
    @StringRes val message: Int? = null
)
