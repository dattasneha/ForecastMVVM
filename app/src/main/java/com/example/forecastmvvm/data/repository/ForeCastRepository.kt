package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.entity.WeatherLocation
import com.example.forecastmvvm.data.db.unlocalized.UnitSpecificCurrentWeatherEntry

interface ForeCastRepository {
    suspend fun getCurrentWeather(metric:Boolean):LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}