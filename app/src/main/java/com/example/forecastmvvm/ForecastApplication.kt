package com.example.forecastmvvm

import android.app.Application
import androidx.sqlite.db.SupportSQLiteCompat
import com.example.forecastmvvm.data.ApixuWeatherApiService
import com.example.forecastmvvm.data.db.ForecastDatabase
import com.example.forecastmvvm.data.network.ConnectivityInterceptor
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.weatherNetworkDataSource
import com.example.forecastmvvm.data.network.weatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.repository.ForeCastRepository
import com.example.forecastmvvm.data.repository.ForeCastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication:Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@ForecastApplication))
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<weatherNetworkDataSource>() with singleton { weatherNetworkDataSourceImpl(instance()) }
        bind<ForeCastRepository>() with singleton { ForeCastRepositoryImpl(instance(),instance())}
    }
}