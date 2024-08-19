package com.example.forecastmvvm.ui.weather.Current

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
    private val group_loading =
        requireView().findViewById<Group>(R.id.group_loading)
    private val textView_temperature =
        requireView().findViewById<TextView>(R.id.textView_temperature)
    private val textView_feelsLike =
        requireView().findViewById<TextView>(R.id.textView_feels_like_temperature)
    private val textView_condition =
        requireView().findViewById<TextView>(R.id.textView_condition)
    private val textView_precipitation =
        requireView().findViewById<TextView>(R.id.textView_precipitation)
    private val textView_wind =
        requireView().findViewById<TextView>(R.id.textView_wind)
    private val textView_visibility =
        requireView().findViewById<TextView>(R.id.textView_visibility)
    private val imageView_condition_icon =
        requireView().findViewById<ImageView>(R.id.imageView_condition_icon)


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

        currentWeather.observe(viewLifecycleOwner,Observer{

            if(it == null) return@Observer
            group_loading.visibility = View.GONE
            updateLocation("Los Angeles")
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(imageView_condition_icon)
        })
    }
    private fun chooseLocalizedUnitAbbreviation(metric: String,imperial: String):String{
        return if (viewModel.isMetric) metric else imperial
    }
    private fun updateLocation(location:String){
        (activity as AppCompatActivity)?.supportActionBar?.title = location
    }
    private fun updateDateToToday(){
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }
    private fun updateTemperatures(temperature:Double,feelsLike:Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C","°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feelsLike.text ="$feelsLike$unitAbbreviation"
    }
    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Preciptiation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}