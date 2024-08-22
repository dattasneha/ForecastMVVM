package com.example.forecastmvvm

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.sqlite.db.SupportSQLiteCompat
import com.example.forecastmvvm.data.ApixuWeatherApiService
import com.example.forecastmvvm.data.db.ForecastDatabase
import com.example.forecastmvvm.data.db.WeatherLocationDao
import com.example.forecastmvvm.data.network.ConnectivityInterceptor
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.weatherNetworkDataSource
import com.example.forecastmvvm.data.network.weatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.provider.LocationProvider
import com.example.forecastmvvm.data.provider.LocationProviderImpl
import com.example.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.data.provider.UnitProviderImpl
import com.example.forecastmvvm.data.repository.ForeCastRepository
import com.example.forecastmvvm.data.repository.ForeCastRepositoryImpl
import com.example.forecastmvvm.ui.weather.Current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication:Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@ForecastApplication))
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<weatherNetworkDataSource>() with singleton { weatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForeCastRepository>() with singleton { ForeCastRepositoryImpl(instance(),instance(),instance(),instance())}
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
       
    }
}