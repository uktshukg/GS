package com.gs.weather.api

import com.gs.weather.fragments.main_frag.models.CityData
import io.reactivex.Single

interface IApiClient {
    fun getCityData(cityname: String): Single<CityData>
}
