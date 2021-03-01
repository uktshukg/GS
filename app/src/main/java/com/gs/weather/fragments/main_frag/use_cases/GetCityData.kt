package com.gs.weather.fragments.main_frag.use_cases

import android.content.Context
import com.gs.base.Result
import com.gs.base.UseCase
import com.gs.weather.api.IApiClient
import com.gs.weather.fragments.main_frag.models.Sys
import com.gs.weather.store.IStore
import com.gs.weather.store.LocalCityData
import com.gs.weather.utilities.SharedPref
import io.reactivex.Observable
import io.reactivex.Single
import java.security.spec.InvalidParameterSpecException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetCityData @Inject constructor(
    private val apiClientImpl: IApiClient,
    private val store: IStore,
    private val context: Context
) :
    UseCase<GetCityData.Request, LocalCityData> {

    override fun execute(req: Request): Observable<Result<LocalCityData>> {
        return UseCase.wrapObservable(
            Utils.isInternetAvailable().flatMap {
                if (it) {
                    apiClientImpl.getCityData(
                        req.cityName
                    ).flatMapObservable { cityData ->
                        val current = System.currentTimeMillis().toString()
                        val localCityData = LocalCityData(
                            name = cityData.name, weather = cityData.weather,
                            main = cityData.main, coord = cityData.coord,
                            clouds = cityData.clouds, id = cityData.id, wind = cityData.wind,
                            date = cityData.dt.toLong(),
                            sys = cityData.sys,
                            readableDate = "",
                            lastUpdated = current
                        )
                        SharedPref.saveData(SharedPref.KEYS.LAST_UPDATED_TIME,current,context )
                        store.saveCityData(localCityData).andThen(
                            store.getWeatherData(city = req.cityName)
                        )
                    }
                } else {
                    if (req.cityName.isBlank()) {
                        store.getFirstCity()
                    } else {
                        store.getWeatherData(city = req.cityName)
                    }
                }
            }.map {
                 val lastUpdated= SharedPref.getData(SharedPref.KEYS.LAST_UPDATED_TIME,context )
                try {
                    lastUpdated?.let { time ->
                        val date1 = Date(time.toLong())
                        val df1: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz")
                        it.lastUpdated = df1.format(date1)
                    }
                }catch (e:Exception){
                    // keep it silent
                }
                val timeInSeconds = it.date
                val date = Date(timeInSeconds * 1000)
                val df: DateFormat = SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz")
                it.readableDate = df.format(date)
                return@map it
            })
    }
    data class Request(val cityName: String)
}