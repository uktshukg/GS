package com.gs.weather.fragments.main_frag

import android.content.Context
import com.gs.base.BasePresenter
import com.gs.base.Result
import com.gs.base.UiState
import com.gs.weather.fragments.main_frag.use_cases.AddCityToFav
import com.gs.weather.fragments.main_frag.use_cases.GetCityData
import io.reactivex.Observable
import io.reactivex.Observable.mergeArray
import javax.inject.Inject

class MainFragPresenter @Inject constructor(
    private val initialState: MainFragContract.State,
    private val getCityData: GetCityData,
    private val addCityToFav : AddCityToFav,
    private val context: Context
) :
    BasePresenter<MainFragContract.State, MainFragContract.PartialState, MainFragContract.ViewEvent>(
        initialState
    ) {
    override fun handle(): Observable<out UiState.Partial<MainFragContract.State>> {
        return mergeArray(
            intent<MainFragContract.Intent.GetCityData>()
                .switchMap { getCityData.execute(GetCityData.Request(it.cityName)) }
                .map {
                    when (it) {
                        is Result.Progress -> MainFragContract.PartialState.NoChange
                        is Result.Success -> {
                            MainFragContract.PartialState.SetResult(it.value)
                        }

                        is Result.Failure -> {
                            emitViewEvent(MainFragContract.ViewEvent.ServerErrorToast)
                            MainFragContract.PartialState.ErrorState

                        }


                    }
                },
            intent<MainFragContract.Intent.AddFavourite>()
                .switchMap { addCityToFav.execute(AddCityToFav.Request(it.id)) }
                .map {
                    when (it) {
                        is Result.Progress -> MainFragContract.PartialState.NoChange
                        is Result.Success -> {
                            MainFragContract.PartialState.SetFavourite(true)
                        }

                        is Result.Failure -> {
                            emitViewEvent(MainFragContract.ViewEvent.ServerErrorToast)
                            MainFragContract.PartialState.ErrorState

                        }


                    }
                })
    }

    override fun reduce(
        currentState: MainFragContract.State,
        partialState: MainFragContract.PartialState
    ): MainFragContract.State {
        return when (partialState) {
            MainFragContract.PartialState.NoChange -> currentState
            MainFragContract.PartialState.ErrorState -> currentState
            is MainFragContract.PartialState.SetResult -> currentState.copy(
                localCityData = partialState.localCityData
            )
            is MainFragContract.PartialState.SetFavourite -> currentState.copy(
                canSetFavourite = partialState.canSetFavourite
            )
        }
    }
}