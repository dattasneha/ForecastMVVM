package com.example.forecastmvvm.data.db.entity


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

const val WEATHER_LOCATION_ID = 0
@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String,
    val lat: Double,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id")
    val tzId: String
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = WEATHER_LOCATION_ID

    val zonedDateTime: ZonedDateTime
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch)
            val zoneId = ZoneId.of(tzId)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}