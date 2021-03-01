package com.gs.weather.api

import com.gs.weather.fragments.main_frag.models.CityData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClient {
    @GET("weather")
    fun getCityData(@Query("q")cityname: String, @Query("appid")appId: String): Single<CityData>
}