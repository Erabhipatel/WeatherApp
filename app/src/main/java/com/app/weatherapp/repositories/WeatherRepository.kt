package com.app.weatherapp.repositories

import com.app.weatherapp.api.WeatherService
import com.app.weatherapp.models.Result
import com.app.weatherapp.models.WeatherDetailsResponse

class WeatherRepository(private val weatherService: WeatherService) {

    suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ): Result<WeatherDetailsResponse> {
        return try {
            Result.Success(weatherService.getWeatherInfo(latitude,
                longitude,
                "imperial",
                "b143bb707b2ee117e62649b358207d3e"))
        }catch (e: Exception){
            e.printStackTrace()
            Result.Error(e)
        }


    }

}