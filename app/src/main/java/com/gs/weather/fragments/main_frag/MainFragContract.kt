package com.gs.weather.fragments.main_frag

import com.gs.base.BaseViewEvent
import com.gs.base.UiState
import com.gs.base.UserIntent

interface MainFragContract {
    data class State(val loading:Boolean= false,
                     val locationId: String?= null, val locationDetails: String?= null, val pricePerMin: Float?= null,
                     val session: Session = Session.UNKOWNN,
                     val duration: Long? = null,
                     val amount : Float?= null,
                     val canShowError: Boolean = false
    ): UiState

    sealed class Session{
        object ONGOING: Session()
        object ENDED: Session()
        object UNKOWNN: Session()
    }
    sealed class ViewEvent : BaseViewEvent {
        object ServerErrorToast : ViewEvent()
        object ResumeTimer : ViewEvent()
        object SubmitData : ViewEvent()
    }
    sealed class Intent : UserIntent {
        data class ScanData(val scanResults: String) : Intent()

        data class CompleteSession(val pair: Pair<String, Float>) : Intent()

        object Load : Intent()
    }

     sealed class PartialState : UiState.Partial<State> {
         object NoChange : PartialState()
         data class SetScanData(val locationId: String?= null, val locationDetails: String?= null, val pricePerMin: Float?= null, val session: Session): PartialState()
         data class SetResult(val amount: Float, val duration: Long, val session: Session) : PartialState()
         object ErrorState: PartialState()
    }

}