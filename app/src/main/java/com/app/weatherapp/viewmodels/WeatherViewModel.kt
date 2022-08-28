package com.app.weatherapp.viewmodels

import android.app.Application
import android.location.Location
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.app.weatherapp.utils.LocationTracker
import com.app.weatherapp.api.WeatherService
import com.app.weatherapp.models.Result
import com.app.weatherapp.models.WeatherDetailsResponse
import com.app.weatherapp.repositories.WeatherRepository
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    val isLoading: ObservableField<Boolean> = ObservableField(false)

    private val weatherService: WeatherService by lazy {
        WeatherService.getWeatherService()
    }

    private val repository: WeatherRepository by lazy {
        WeatherRepository(weatherService)
    }

    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(application)
    }

    private val locationTracker: LocationTracker by lazy {
        LocationTracker(
            application,
            fusedLocationProviderClient
        )
    }

    private val _locationLiveData: MutableLiveData<Result<Location>> by lazy {
        MutableLiveData<Result<Location>>()
    }
    val locationLiveData: LiveData<Result<Location>>
        get() = _locationLiveData

    private val _weatherLiveData: MutableLiveData<Result<WeatherDetailsResponse>> by lazy {
        MutableLiveData<Result<WeatherDetailsResponse>>()
    }
    val weatherLiveData: LiveData<Result<WeatherDetailsResponse>>
        get() = _weatherLiveData


    fun getLocation() {
        viewModelScope.launch {
            _locationLiveData.value = locationTracker.getLocation()
        }
    }

    fun getWeatherData(latitude: Double, longitude: Double) {
        isLoading.set(true)
        viewModelScope.launch {
            _weatherLiveData.value = repository.getWeatherDetails(latitude, longitude)
        }
    }
}