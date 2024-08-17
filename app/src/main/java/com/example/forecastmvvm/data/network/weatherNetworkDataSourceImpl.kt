package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.ApixuWeatherApiService
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.internal.NoConnectivityException

class weatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : weatherNetworkDataSource {

    private val  _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, airQuality: String) {
       try {
           val fetchCurrentWeather =  apixuWeatherApiService
               .getCurrentWeather(location,airQuality)
               .await()
           _downloadedCurrentWeather.postValue(fetchCurrentWeather)
       }
       catch (e:NoConnectivityException){
           Log.e("Connectibity","No internet connection",e)
       }
    }
}