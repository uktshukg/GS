package com.gs.base

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface Presenter<S : UiState,  E : BaseViewEvent> {
    // delivers the current state
    fun state(): Observable<S>

    // responsible for attaching all user intents to a single observable
    fun attachIntents(intents: Observable<UserIntent>): Disposable

    // adds all view events observables
    fun viewEvent(): Observable<E>
}
