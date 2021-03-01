package com.gs.weather.store

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StoreImpl @Inject constructor(private val weatherDataBaseDao: WeatherDataBaseDao) : IStore {
    override fun getWeatherData(city: String): Observable<LocalCityData> {
        return weatherDataBaseDao.getCityData(city)
    }

    override fun getAllFavouriteCityData(): Observable<List<LocalCityData>> {
        return weatherDataBaseDao.getAllCitites()
    }

    override fun saveCityData(localCityData: LocalCityData): Completable {
        return weatherDataBaseDao.saveCityData(localCityData)
    }

    override fun updateFav(cityId: Int, isFavourite: Boolean): Completable {
        return weatherDataBaseDao.updateFav(cityId, isFavourite)
    }

    override fun getFirstCity(): Single<LocalCityData> {
        return weatherDataBaseDao.getFirstCity()
    }
}