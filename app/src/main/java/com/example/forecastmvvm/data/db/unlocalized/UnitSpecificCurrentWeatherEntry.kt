package com.example.forecastmvvm.data.db.unlocalized

import androidx.room.ColumnInfo

interface UnitSpecificCurrentWeatherEntry {

    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double

}