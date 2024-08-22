package com.example.forecastmvvm.ui.weather.Current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import com.example.forecastmvvm.R
import com.example.forecastmvvm.internal.glide.GlideApp

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
    private lateinit var grouploading : Group
    private lateinit var textView_temperature:TextView
    private lateinit var textView_feelsLike :TextView
    private lateinit var textView_condition :TextView
    private lateinit var textView_precipitation :TextView
    private lateinit var textView_wind :TextView
    private lateinit var textView_visibility :TextView
    private lateinit var imageView_condition_icon :ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,viewModelFactory)[CurrentWeatherViewModel::class.java]

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
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner,Observer{
            if(it == null) return@Observer
            updateLocation(it.name)
        })

        currentWeather.observe(viewLifecycleOwner,Observer{

            if(it == null) return@Observer
            grouploading = requireView().findViewById(R.id.group_loading)
            grouploading.visibility = View.GONE

            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            imageView_condition_icon =
                requireView().findViewById(R.id.imageView_condition_icon)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https:${it.conditionIconUrl}")
                .into(imageView_condition_icon)
        })
    }
    private fun chooseLocalizedUnitAbbreviation(metric: String,imperial: String):String{
        return if (viewModel.isMetric) metric else imperial
    }
    private fun updateLocation(location:String){
        (activity as AppCompatActivity).supportActionBar?.title = location
    }
    private fun updateDateToToday(){
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
    }
    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(temperature:Double, feelsLike:Double){
        textView_temperature =
            requireView().findViewById(R.id.textView_temperature)
        textView_feelsLike =
            requireView().findViewById(R.id.textView_feels_like_temperature)
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C","°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feelsLike.text ="$feelsLike$unitAbbreviation"
    }
    private fun updateCondition(condition: String) {
        textView_condition =
            requireView().findViewById(R.id.textView_condition)
        textView_condition.text = condition
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrecipitation(precipitationVolume: Double) {
        textView_precipitation =
            requireView().findViewById(R.id.textView_precipitation)
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Preciptiation: $precipitationVolume $unitAbbreviation"
    }

    @SuppressLint("SetTextI18n")
    private fun updateWind(windDirection: String, windSpeed: Double) {
        textView_wind =
            requireView().findViewById(R.id.textView_wind)
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    @SuppressLint("SetTextI18n")
    private fun updateVisibility(visibilityDistance: Double) {
        textView_visibility =
            requireView().findViewById(R.id.textView_visibility)
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}