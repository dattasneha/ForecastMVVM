package com.example.forecastmvvm.ui.weather.Current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.data.repository.ForeCastRepository

class CurrentWeatherViewModelFactory(
    private val foreCastRepository: ForeCastRepository,
    private val unitProvider: UnitProvider
):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(foreCastRepository,unitProvider)  as T
    }
}