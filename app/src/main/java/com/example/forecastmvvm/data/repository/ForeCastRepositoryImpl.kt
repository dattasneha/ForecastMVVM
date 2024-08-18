package com.example.forecastmvvm.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.CurrentWeatherDao
import com.example.forecastmvvm.data.db.unlocalized.UnitSpecificCurrentWeatherEntry
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.data.network.weatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ForeCastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: weatherNetworkDataSource
): ForeCastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather:CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initWeatherData(){
         if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
             fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Kolkata",
            "no"
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchedTime:ZonedDateTime):Boolean{
         val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
         return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}