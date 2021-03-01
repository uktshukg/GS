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

class AddCityToFav @Inject constructor(
    private val apiClientImpl: IApiClient,
    private val context: Context,
    private val store: IStore
) :
    UseCase<AddCityToFav.Request, Unit> {

    override fun execute(req: Request): Observable<Result<Unit>> {

        return UseCase.wrapCompletable(
            store.updateFav(
                req.cityId
            ))

    }

    data class Request(val cityId: Int)

}
