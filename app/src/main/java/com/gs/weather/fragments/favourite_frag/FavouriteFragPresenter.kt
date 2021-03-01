package com.gs.weather.fragments.favourite_frag

import android.content.Context
import com.gs.base.BasePresenter
import com.gs.base.Result
import com.gs.base.UiState
import com.gs.weather.fragments.favourite_frag.use_cases.GetAllFavList
import io.reactivex.Observable
import io.reactivex.Observable.mergeArray
import javax.inject.Inject

class FavouriteFragPresenter @Inject constructor(
    private val initialState: FavouriteFragContract.State,
    private val getAllFavList: GetAllFavList,
    private val context: Context
) :
    BasePresenter<FavouriteFragContract.State, FavouriteFragContract.PartialState, FavouriteFragContract.ViewEvent>(
        initialState
    ) {
    override fun handle(): Observable<out UiState.Partial<FavouriteFragContract.State>> {
        return mergeArray(
            intent<FavouriteFragContract.Intent.Load>()
                .switchMap { getAllFavList.execute(Unit) }
                .map {
                    when (it) {
                        is Result.Progress -> FavouriteFragContract.PartialState.NoChange
                        is Result.Success -> {
                            FavouriteFragContract.PartialState.SetResult(it.value)
                        }
                        is Result.Failure -> {
                            emitViewEvent(FavouriteFragContract.ViewEvent.ErrorToast)
                            FavouriteFragContract.PartialState.NoChange

                        }

                    }
                })
    }

    override fun reduce(
        currentState: FavouriteFragContract.State,
        partialState: FavouriteFragContract.PartialState
    ): FavouriteFragContract.State {
        return when (partialState) {
            FavouriteFragContract.PartialState.NoChange -> currentState
            is FavouriteFragContract.PartialState.SetResult -> currentState.copy(
                list = partialState.list
            )
        }
    }
}