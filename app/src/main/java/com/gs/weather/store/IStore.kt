package com.gs.weather.store

import com.gs.weather.fragments.main_frag.models.Weather
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

interface IStore {
    fun getWeatherData( city:String): Observable<LocalCityData>
    fun  getAllFavouriteCityData(): Observable<List<LocalCityData>>
    fun saveCityData(localCityData: LocalCityData):Completable
    fun updateFav(cityId: Int): Completable
}