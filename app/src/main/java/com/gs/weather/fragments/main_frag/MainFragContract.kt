package com.gs.weather.fragments.main_frag

import com.gs.base.BaseViewEvent
import com.gs.base.UiState
import com.gs.base.UserIntent
import com.gs.weather.store.LocalCityData

interface MainFragContract {
    data class State(
        val loading: Boolean = false,
        val localCityData: LocalCityData? = null,
        val canSetFavourite: Boolean = false
    ) : UiState


    sealed class ViewEvent : BaseViewEvent {
        object ErrorToast : ViewEvent()
        object NoDataFound : ViewEvent()
    }

    sealed class Intent : UserIntent {
        data class GetCityData(val cityName: String) : Intent()
        data class CompleteSession(val pair: Pair<String, Float>) : Intent()
        data class AddFavourite(val id: Pair<Int, Boolean>) : Intent()
        object Load : Intent()
    }

    sealed class PartialState : UiState.Partial<State> {
        object NoChange : PartialState()
        data class ShowLoader(val canShowLoader: Boolean) : PartialState ()
        data class SetResult(val localCityData: LocalCityData) : PartialState()
        data class SetFavourite(val canSetFavourite: Boolean) : PartialState()
    }

}