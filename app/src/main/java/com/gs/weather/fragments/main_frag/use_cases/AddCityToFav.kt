package com.gs.weather.fragments.main_frag.use_cases

import com.gs.base.Result
import com.gs.base.UseCase
import com.gs.weather.store.IStore
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class AddCityToFav @Inject constructor(
    private val store: IStore
) :
    UseCase<AddCityToFav.Request, Boolean> {

    override fun execute(req: Request): Observable<Result<Boolean>> {

        return UseCase.wrapSingle(
            store.updateFav(
                req.pair.first,
                req.pair.second
            ).andThen(Single.just(req.pair.second))
        )

    }

    // pair of id and isFavourite
    data class Request(val pair: Pair<Int, Boolean>)

}
