package com.gs.weather.fragments.main_frag

import android.content.Context
import androidx.room.EmptyResultSetException
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
    private val addCityToFav: AddCityToFav,
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
                        is Result.Progress ->  MainFragContract.PartialState.ShowLoader(true)
                        is Result.Success -> {
                            MainFragContract.PartialState.SetResult(it.value)
                        }
                        is Result.Failure -> {
                            emitViewEvent(MainFragContract.ViewEvent.ErrorToast)
                            MainFragContract.PartialState.ShowLoader(false)
                        }
                    }
                },
           getCityData.execute(GetCityData.Request(""))
                .map {
                    when (it) {
                        is Result.Progress ->  MainFragContract.PartialState.ShowLoader(true)
                        is Result.Success -> {
                            MainFragContract.PartialState.SetResult(it.value)
                        }
                        is Result.Failure -> {
                            when (it.error) {
                                is EmptyResultSetException -> emitViewEvent(MainFragContract.ViewEvent.NoDataFound)
                                else -> emitViewEvent(MainFragContract.ViewEvent.ErrorToast)
                            }

                            MainFragContract.PartialState.ShowLoader(false)
                        }
                    }
                },
            intent<MainFragContract.Intent.AddFavourite>()
                .switchMap { addCityToFav.execute(AddCityToFav.Request(it.id)) }
                .map {
                    when (it) {
                        is Result.Progress -> MainFragContract.PartialState.ShowLoader(true)
                        is Result.Success -> {
                            MainFragContract.PartialState.SetFavourite(it.value)
                        }
                        is Result.Failure -> {
                            emitViewEvent(MainFragContract.ViewEvent.ErrorToast)
                            MainFragContract.PartialState.ShowLoader(false)
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
            is MainFragContract.PartialState.SetResult -> currentState.copy(
                localCityData = partialState.localCityData, loading = false
            )
            is MainFragContract.PartialState.SetFavourite -> currentState.copy(
                canSetFavourite = partialState.canSetFavourite, loading = false
            )
            is MainFragContract.PartialState.ShowLoader -> currentState.copy(loading = partialState.canShowLoader)
        }
    }
}