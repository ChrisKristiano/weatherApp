package com.example.weatherapp.presentation.common

import android.content.Context
import android.net.ConnectivityManager

object NetworkService {

    fun isConnected(context: Context): Boolean {
        val manager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        return manager.activeNetwork != null
    }
}