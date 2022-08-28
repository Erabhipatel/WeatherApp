package com.app.weatherapp.api

import com.app.weatherapp.models.Weather
import com.app.weatherapp.models.WeatherDetailsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("2.5/onecall")
    suspend fun getWeatherInfo(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String,
    ): WeatherDetailsResponse


    companion object {
        private const val BASE_URL = " https://api.openweathermap.org/data/"
        private var weatherService: WeatherService? = null

        fun getWeatherService(): WeatherService {
            if (weatherService == null) {
                weatherService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherService::class.java)
            }
            return weatherService!!
        }

    }
}