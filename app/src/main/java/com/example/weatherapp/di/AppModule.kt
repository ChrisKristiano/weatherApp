package com.example.weatherapp.di

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.example.weatherapp.android.GeoLocationManagerImpl
import com.example.weatherapp.android.PermissionManagerImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.manager.GeoLocationManager
import com.example.weatherapp.domain.manager.PermissionManager
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.open-meteo.com")
        .build()
        .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository = WeatherRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext appContext: Context): LocationManager =
        appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    @Singleton
    fun provideGeoLocationManager(@ApplicationContext appContext: Context): GeoLocationManager =
        GeoLocationManagerImpl(geocoder = Geocoder(appContext, Locale.getDefault()))

    @Provides
    @Singleton
    fun providePermissionManager(@ApplicationContext appContext: Context): PermissionManager =
        PermissionManagerImpl(appContext)
}