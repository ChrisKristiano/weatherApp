package com.example.weatherapp.presentation.util.extensions

fun String?.orDash(): String = this.takeIf { !it.isNullOrBlank() && it != "null" } ?: "-"