package com.gs.weather.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDataBaseDao {


    @Query("select * from LocalCityData where name like :city ")
    fun getCityData(city: String): Observable<LocalCityData>

    @Query("select * from LocalCityData where isFavourite =1")
    fun getAllCitites(): Observable<List<LocalCityData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCityData(localCityData: LocalCityData): Completable

    @Query("update localcitydata set  isFavourite =:isFavourite where id=:cityId")
    fun updateFav(cityId: Int, isFavourite: Boolean): Completable

    @Query("select * from LocalCityData limit 1")
    fun getFirstCity(): Single<LocalCityData>

}