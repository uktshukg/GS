package com.gs.weather.fragments.favourite_frag

import com.gs.base.BaseViewEvent
import com.gs.base.UiState
import com.gs.base.UserIntent

interface FavouriteFragContract {
    data class State(val list:List<String> = mutableListOf()
    ): UiState

    sealed class ViewEvent : BaseViewEvent {
        object ServerErrorToast : ViewEvent()
        object SubmitData : ViewEvent()
    }
    sealed class Intent : UserIntent {
        data class ScanData(val scanResults: String) : Intent()

        data class CompleteSession(val pair: Pair<String, Float>) : Intent()

        object Load : Intent()
    }

     sealed class PartialState : UiState.Partial<State> {
         data class SetResult(val list: List<String>) : PartialState()
         object NoChange : PartialState()
         object ErrorState: PartialState()
    }

}