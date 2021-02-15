package com.gs.weather.fragments.main_frag

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.gs.base.BaseFragment
import com.gs.base.UserIntent
import com.gs.weather.R
import com.gs.weather.utilities.SharedPref
import com.gs.weather.utilities.convertMILLISToStandard
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class MainFrag :
        BaseFragment<MainFragContract.State, MainFragContract.ViewEvent, MainFragContract.Intent>(R.layout.fragment_blank) {
    private var timerDisposable: Disposable? = null
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    private var time: TextView? = null
    private var scanSubject: PublishSubject<String> = PublishSubject.create()
    private var completeSession: PublishSubject<Pair<String, Float>> = PublishSubject.create()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun userIntents(): Observable<UserIntent> {
        return Observable.mergeArray(
                Observable.just(MainFragContract.Intent.Load),

                scanSubject.map {
                    MainFragContract.Intent.ScanData(it)
                },
                completeSession.map {
                    MainFragContract.Intent.CompleteSession(it)
                }
        )
    }

    override fun render(state: MainFragContract.State) {

    }


    override fun onPause() {
        timerDisposable?.dispose()
        timerDisposable = null
        super.onPause()
    }

    override fun handleViewEvent(event: MainFragContract.ViewEvent) {
        when (event) {
            MainFragContract.ViewEvent.ServerErrorToast -> showErrorToast()
            MainFragContract.ViewEvent.ResumeTimer -> startTimer()
            MainFragContract.ViewEvent.SubmitData -> showSubmitToast()
        }
    }

    private fun startTimer() {
        time?.let { tv ->
            val startTime =
                    SharedPref.getLong(context, context!!.getString(R.string.start_time))
            if (timerDisposable == null) {
                timerDisposable =
                        Observable.interval(1, TimeUnit.SECONDS).observeOn(schedulerProvider)
                                .subscribe {
                                    tv.text =
                                            convertMILLISToStandard(System.currentTimeMillis() - startTime)
                                }
            }
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun showErrorToast() {
        Toast.makeText(context!!, "Something happened worng", Toast.LENGTH_LONG).show()
    }

    private fun showSubmitToast() {
        Toast.makeText(context!!, "Submitting Data", Toast.LENGTH_LONG).show()
    }
}