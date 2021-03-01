package com.gs.weather.fragments.main_frag.models

import androidx.room.TypeConverters
import com.gs.weather.store.WeatherTypeConvertor


data class CityData(

		val coord: Coord,
		@TypeConverters(WeatherTypeConvertor::class)
		val weather: List<Weather>,
		val base: String,
		val main: Main,
		val visibility: Int,
		val wind: Wind,
		val clouds: Clouds,
		val dt: Int,
		val sys: Sys,
		val timezone: Int,
		val id: Int,
		val name: String,
		val cod: Int
)