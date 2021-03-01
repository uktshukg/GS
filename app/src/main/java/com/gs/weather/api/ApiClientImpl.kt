package com.gs.weather.api

import com.gs.weather.fragments.main_frag.models.CityData
import io.reactivex.Single
import javax.inject.Inject

class ApiClientImpl @Inject constructor(private val apiClient: ApiClient) : IApiClient {


    override fun getCityData(cityname: String): Single<CityData> {
        return apiClient.getCityData(cityname, "a9ab25b9698d760b432d2182f7f6c44f")
    }

}