package com.example.weatherapp.presentation.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.WeatherCodeType

class WeatherCodeTranslator {
    companion object {

        @StringRes
        fun toStatusStringRes(weatherCode: WeatherCodeType?): Int = when (weatherCode) {
            WeatherCodeType.CLEAR_SKY -> R.string.weather_code_clear_sky
            WeatherCodeType.MAINLY_CLEAR -> R.string.weather_code_mainly_clear
            WeatherCodeType.PARTLY_CLOUDY -> R.string.weather_code_partly_cloudy
            WeatherCodeType.MOSTLY_CLOUDY -> R.string.weather_code_cloudy
            WeatherCodeType.FOG -> R.string.weather_code_fog
            WeatherCodeType.DRIZZLE -> R.string.weather_code_drizzle
            WeatherCodeType.FREEZING_DRIZZLE -> R.string.weather_code_freezing_drizzle
            WeatherCodeType.RAIN -> R.string.weather_code_rain
            WeatherCodeType.FREEZING_RAIN -> R.string.weather_code_freezing_rain
            WeatherCodeType.RAIN_SHOWERS -> R.string.weather_code_shower_rain
            WeatherCodeType.SNOW_FALL -> R.string.weather_code_snow_fall
            WeatherCodeType.SNOW_GRAINS -> R.string.weather_code_snow_grains
            WeatherCodeType.SNOW_SHOWERS -> R.string.weather_code_snow_showers
            WeatherCodeType.THUNDERSTORM -> R.string.weather_code_thunderstorm
            null -> R.string.nothing
        }

        @DrawableRes
        fun toBackgroundDrawableRes(weatherCode: WeatherCodeType?, isDay: Boolean?): Int = when (isDay) {
            false -> R.drawable.thunder
            else -> when (weatherCode) {
                WeatherCodeType.CLEAR_SKY,
                WeatherCodeType.MAINLY_CLEAR -> R.drawable.sunny

                WeatherCodeType.PARTLY_CLOUDY -> R.drawable.partly_sunny

                WeatherCodeType.MOSTLY_CLOUDY,
                WeatherCodeType.FOG -> R.drawable.cloud

                WeatherCodeType.DRIZZLE,
                WeatherCodeType.FREEZING_DRIZZLE,
                WeatherCodeType.RAIN,
                WeatherCodeType.FREEZING_RAIN,
                WeatherCodeType.RAIN_SHOWERS -> R.drawable.rain

                WeatherCodeType.SNOW_FALL,
                WeatherCodeType.SNOW_GRAINS,
                WeatherCodeType.SNOW_SHOWERS -> R.drawable.snow

                WeatherCodeType.THUNDERSTORM -> R.drawable.thunder
                null -> R.drawable.partly_sunny
            }
        }

        @DrawableRes
        fun toIconDrawableRes(code: WeatherCodeType?, isDay: Boolean?): Int = when {
            (WeatherCodeType.CLEAR_SKY == code || WeatherCodeType.MAINLY_CLEAR == code) && isDay == true -> R.drawable.icon_sunny
            (WeatherCodeType.CLEAR_SKY == code || WeatherCodeType.MAINLY_CLEAR == code) && isDay == false -> R.drawable.icon_night
            (WeatherCodeType.PARTLY_CLOUDY == code || WeatherCodeType.MOSTLY_CLOUDY == code) && isDay == true -> R.drawable.icon_cloudy_day
            (WeatherCodeType.PARTLY_CLOUDY == code || WeatherCodeType.MOSTLY_CLOUDY == code) && isDay == false -> R.drawable.icon_cloudy_night
            WeatherCodeType.FOG == code -> R.drawable.icon_cloudy
            WeatherCodeType.DRIZZLE == code || WeatherCodeType.FREEZING_DRIZZLE == code -> R.drawable.icon_drizzle
            WeatherCodeType.RAIN == code || WeatherCodeType.FREEZING_RAIN == code -> R.drawable.icon_rain
            WeatherCodeType.RAIN_SHOWERS == code -> R.drawable.icon_rain_showers
            WeatherCodeType.SNOW_GRAINS == code -> R.drawable.icon_snow_grains
            WeatherCodeType.SNOW_FALL == code -> R.drawable.icon_snow_fall
            WeatherCodeType.SNOW_SHOWERS == code -> R.drawable.icon_snow_shower
            WeatherCodeType.THUNDERSTORM == code -> R.drawable.icon_thunder
            else -> R.drawable.icon_cloudy
        }
    }
}