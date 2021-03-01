package com.gs.weather.fragments.favourite_frag.use_cases

import com.gs.base.Result
import com.gs.base.UseCase
import com.gs.weather.store.IStore
import io.reactivex.Observable
import javax.inject.Inject

class GetAllFavList @Inject constructor(
    private val store: IStore
) :
    UseCase<Unit, List<String>> {

    override fun execute(req: Unit): Observable<Result<List<String>>> {
        return UseCase.wrapObservable(store.getAllFavouriteCityData().map {
            it.map {
                it.name
            }
        })
    }

}
