package com.example.forecastmvvm.ui.weather.Current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.data.repository.ForeCastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.example.forecastmvvm.internal.lazyDeffered

class CurrentWeatherViewModel(
    private val foreCastRepository: ForeCastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC
    val weather by lazyDeffered {
        foreCastRepository.getCurrentWeather(isMetric)
    }

}