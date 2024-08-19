package com.example.forecastmvvm.ui.weather.Current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.repository.ForeCastRepository

class CurrentWeatherViewModelFactory(
    private val foreCastRepository: ForeCastRepository
):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(foreCastRepository) as T
    }
}