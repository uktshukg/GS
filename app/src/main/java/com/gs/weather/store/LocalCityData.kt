package com.gs.weather.store

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gs.weather.fragments.main_frag.models.*

@Entity
data class LocalCityData(
    @TypeConverters(WeatherTypeConvertor::class)
    val weather: List<Weather>,
    val isFavourite: Boolean = false,
    @PrimaryKey val id: Int,
    val name: String,
    @Embedded val wind: Wind,
    @Embedded val clouds: Clouds,
    @Embedded val coord: Coord,
    @Embedded val main: Main,
    @Embedded val sys: Sys,
    val date:Long,
    var readableDate: String
)


