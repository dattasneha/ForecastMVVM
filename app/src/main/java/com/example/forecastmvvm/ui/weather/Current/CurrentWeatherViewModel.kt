package com.example.forecastmvvm.ui.weather.Current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForeCastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.example.forecastmvvm.internal.lazyDeffered

class CurrentWeatherViewModel(
    private val foreCastRepository: ForeCastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC
    val isMetric:Boolean
        get() = unitSystem == UnitSystem.METRIC
    val weather by lazyDeffered {
        foreCastRepository.getCurrentWeather(isMetric)
    }

}