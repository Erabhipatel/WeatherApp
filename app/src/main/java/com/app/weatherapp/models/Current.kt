package com.app.weatherapp.models

data class Current(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Float,
    val feels_like: Float,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Float,
    val uvi: Float,
    val clouds: Float,
    val visibility: Float,
    val wind_speed: Float,
    val wind_deg: Int,
    val weather: List<Weather>
)
