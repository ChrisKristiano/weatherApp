package com.example.weatherapp.android

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.weatherapp.domain.manager.PermissionManager

class PermissionManagerImpl(
    private val appContext: Context
) : PermissionManager {

    override fun isLocationPermissionGranted(): Boolean =
        isFineLocationGranted() || isCoarseLocationGranted()

    private fun isFineLocationGranted(): Boolean = ActivityCompat.checkSelfPermission(
        appContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun isCoarseLocationGranted(): Boolean = ActivityCompat.checkSelfPermission(
        appContext,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}