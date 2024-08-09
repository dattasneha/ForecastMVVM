package com.example.forecastmvvm.data

import com.example.forecastmvvm.data.response.CurrentWeatherResponse
import com.example.forecastmvvm.data.response.Location
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY="fba9573acc654d7a995110405240808"
//http://api.weatherapi.com/v1/current.json?key=fba9573acc654d7a995110405240808&q=Kolkata&aqi=no
interface ApixuWeatherApiService {
    @GET("current.json")
    fun getCurrentWeather(
         @Query("q") location: String,
         @Query("aqi") airQuality:String
    ):Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke():ApixuWeatherApiService{
            val requestInterceptor = Interceptor {chain ->
                val url= chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)

        }
    }
}