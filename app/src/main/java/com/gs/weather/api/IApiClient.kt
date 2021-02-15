package com.gs.weather.api

import com.gs.weather.fragments.main_frag.models.StayDetails
import io.reactivex.Completable

interface IApiClient {
    fun sendData( body: StayDetails): Completable
}
