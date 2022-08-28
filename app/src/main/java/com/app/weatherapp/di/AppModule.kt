package com.app.weatherapp.di

//import android.app.Application
//import com.app.weatherapp.utils.LocationTracker
//import com.app.weatherapp.api.WeatherService
//import com.app.weatherapp.db.UserDao
//import com.app.weatherapp.db.UserDatabase
//import com.app.weatherapp.repositories.MainRepository
////import com.app.weatherapp.viewmodels.ViewModelFactory
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class AppModule() {
//
//    @Provides
//    fun provideWeatherService(): WeatherService{
//        return Retrofit.Builder()
//            .baseUrl(WeatherService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(WeatherService::class.java)
//    }
//
//    @Provides
//    fun provideFusedLocationProvider(application: Application): FusedLocationProviderClient{
//        return LocationServices.getFusedLocationProviderClient(application)
//    }
//
//
//    @Provides
//    fun providesLocationTracker(application: Application, fusedLocationProviderClient: FusedLocationProviderClient)
//    :LocationTracker{
//        return LocationTracker(application, fusedLocationProviderClient)
//    }
//
//    @Provides
//    fun provideDao(application: Application):UserDao{
//        return UserDatabase.invoke(application).userDao()
//    }
//
//    @Provides
//    fun provideMainRepository(userDao: UserDao):MainRepository{
//        return MainRepository(userDao)
//    }
//
//    @Provides
//    fun provideViewModelFactory(application: Application, repository: MainRepository,
//    locationTracker: LocationTracker):ViewModelFactory{
//        return ViewModelFactory(application, repository, locationTracker)
//    }
//}