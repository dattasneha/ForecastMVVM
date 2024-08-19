package com.example.forecastmvvm.ui.weather.Current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.ApixuWeatherApiService
import com.example.forecastmvvm.data.network.ConnectivityInterceptor
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.weatherNetworkDataSource
import com.example.forecastmvvm.data.network.weatherNetworkDataSourceImpl
import com.example.forecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment :ScopedFragment(), KodeinAware{
    override val kodein by closestKodein()
     private val viewModelFactory : CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
        // TODO: Use the ViewModel
//        val apiService = ApixuWeatherApiService(ConnectivityInterceptorImpl(this.requireContext()))
//        val weatherNetworkDataSource = weatherNetworkDataSourceImpl(apiService)
//        weatherNetworkDataSource.downloadedCurrentWeather.observe(this.viewLifecycleOwner,Observer{
//            val textview =requireView().findViewById<TextView>(R.id.textView)
//            textview.text= it.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main){
//            weatherNetworkDataSource.fetchCurrentWeather("Kolkata","no")
//        }
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        var textview = requireView().findViewById<TextView>(R.id.textView)
        currentWeather.observe(viewLifecycleOwner,Observer{
            if(it == null) return@Observer
            textview.text = it.toString()
        })
    }

}