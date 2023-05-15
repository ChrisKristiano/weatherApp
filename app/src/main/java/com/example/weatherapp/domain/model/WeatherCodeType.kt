package com.example.weatherapp.domain.model

enum class WeatherCodeType {
    CLEAR_SKY,
    MAINLY_CLEAR,
    PARTLY_CLOUDY,
    MOSTLY_CLOUDY,
    FOG,
    DRIZZLE,
    FREEZING_DRIZZLE,
    RAIN,
    FREEZING_RAIN,
    RAIN_SHOWERS,
    SNOW_FALL,
    SNOW_GRAINS,
    SNOW_SHOWERS,
    THUNDERSTORM;

    companion object {
        fun fromCode(code: Int?): WeatherCodeType? = when (code) {
            0 -> CLEAR_SKY
            1 -> MAINLY_CLEAR
            2 -> PARTLY_CLOUDY
            3 -> MOSTLY_CLOUDY
            45, 48 -> FOG
            51, 53, 55 -> DRIZZLE
            56, 57 -> FREEZING_DRIZZLE
            61, 63, 65 -> RAIN
            66, 67 -> FREEZING_RAIN
            80, 81, 82 -> RAIN_SHOWERS
            71, 73, 75 -> SNOW_FALL
            77 -> SNOW_GRAINS
            85, 86 -> SNOW_SHOWERS
            95, 96, 99 -> THUNDERSTORM
            else -> null
        }
    }
}