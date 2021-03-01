package com.gs.weather.fragments.main_frag.use_cases

import android.content.Context
import com.gs.base.Result
import com.gs.base.UseCase
import com.gs.weather.api.IApiClient
import com.gs.weather.store.IStore
import com.gs.weather.store.LocalCityData
import io.reactivex.Observable
import io.reactivex.Single
import java.security.InvalidParameterException
import javax.inject.Inject

class GetCityData @Inject constructor(
    private val apiClientImpl: IApiClient,
    private val context: Context,
    private val store: IStore
) :
    UseCase<GetCityData.Request, LocalCityData> {

    override fun execute(req: Request): Observable<Result<LocalCityData>> {
        if (req.cityName.isEmpty()) {
            throw  InvalidParameterException()
        }

        return UseCase.wrapSingle(
            apiClientImpl.getCityData(
                req.cityName
            ).flatMap { cityData ->
                val localCityData = LocalCityData(
                    name = cityData.name, weather = cityData.weather,
                    main = cityData.main, coord = cityData.coord,
                    clouds = cityData.clouds, id = cityData.id, wind = cityData.wind
                )
                store.saveCityData(localCityData).andThen(
                    Single.just(localCityData)
                )

            })

    }

    data class Request(val cityName: String)

}