package com.gs.weather.store

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IStore {
    fun getWeatherData( city:String): Observable<LocalCityData>
    fun  getAllFavouriteCityData(): Observable<List<LocalCityData>>
    fun saveCityData(localCityData: LocalCityData):Completable
    fun updateFav(cityId: Int, isFavourite: Boolean): Completable
    fun getFirstCity(): Single<LocalCityData>
}