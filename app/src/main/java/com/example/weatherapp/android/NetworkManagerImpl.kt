package com.example.weatherapp.android

import android.content.Context
import android.net.ConnectivityManager
import com.example.weatherapp.domain.manager.NetworkManager

class NetworkManagerImpl(
    private val appContext: Context
) : NetworkManager {

    override fun isConnected(): Boolean =
        (appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetwork != null
}