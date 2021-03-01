package com.gs.weather.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gs.weather.fragments.main_frag.models.Weather
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WeatherDataBaseDao {


    @Query("select * from LocalCityData where id=:city ")
    fun getCityData(city: String): Observable<LocalCityData>

    @Query("select * from LocalCityData")
    fun getAllCitites(): Observable<List<LocalCityData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCityData(localCityData: LocalCityData): Completable

    @Query("update localcitydata set  isFavourite =0 where id=:cityId")
    fun updateFav(cityId: Int): Completable

}