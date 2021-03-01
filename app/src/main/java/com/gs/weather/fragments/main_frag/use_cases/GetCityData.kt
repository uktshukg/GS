package com.gs.weather.fragments.main_frag.use_cases

import android.content.Context
import com.gs.base.Result
import com.gs.base.UseCase
import com.gs.weather.api.IApiClient
import com.gs.weather.store.IStore
import com.gs.weather.store.LocalCityData
import io.reactivex.Observable
import io.reactivex.Single
import isInternetAvailable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetCityData @Inject constructor(
    private val apiClientImpl: IApiClient,
    private val store: IStore
) :
    UseCase<GetCityData.Request, LocalCityData> {

    override fun execute(req: Request): Observable<Result<LocalCityData>> {
        return UseCase.wrapSingle(
            isInternetAvailable().firstOrError().flatMap {
                if (it) {
                    apiClientImpl.getCityData(
                        req.cityName
                    ).flatMap { cityData ->
                        val localCityData = LocalCityData(
                            name = cityData.name, weather = cityData.weather,
                            main = cityData.main, coord = cityData.coord,
                            clouds = cityData.clouds, id = cityData.id, wind = cityData.wind,
                            date = cityData.dt.toLong(),
                            sys = cityData.sys,
                            readableDate = ""
                        )
                        store.saveCityData(localCityData).andThen(
                            Single.just(localCityData)
                        )
                    }
                } else {
                    if (req.cityName.isBlank()) {
                        store.getFirstCity()
                    } else {
                        store.getWeatherData(city = req.cityName).firstOrError()
                    }
                }
            }.map {
                val timeInSeconds = it.date
                val date = Date(timeInSeconds * 1000)
                val df: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz")
                it.readableDate = df.format(date)
                return@map it
            })
    }
    data class Request(val cityName: String)
}