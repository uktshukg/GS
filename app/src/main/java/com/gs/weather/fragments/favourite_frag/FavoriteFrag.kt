package com.gs.weather.fragments.favourite_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gs.base.BaseFragment
import com.gs.base.UserIntent
import com.gs.weather.R
import com.gs.weather.databinding.FavouriteFragBinding
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


class FavoriteFrag :
    BaseFragment<FavouriteFragContract.State, FavouriteFragContract.ViewEvent, FavouriteFragContract.Intent>(
        R.layout.favourite_frag
    ) {
    private lateinit var adpater: FavouriteAdapter
    private var scanSubject: PublishSubject<String> = PublishSubject.create()
    private var completeSession: PublishSubject<Pair<String, Float>> = PublishSubject.create()
    private var _binding: FavouriteFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavouriteFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         adpater= FavouriteAdapter()
        binding.recyclerView.adapter = this.adpater
    }

    override fun userIntents(): Observable<UserIntent> {
        return Observable.mergeArray(
            Observable.just(FavouriteFragContract.Intent.Load),

            scanSubject.map {
                FavouriteFragContract.Intent.ScanData(it)
            },
            completeSession.map {
                FavouriteFragContract.Intent.CompleteSession(it)
            }
        )
    }

    override fun render(state: FavouriteFragContract.State) {

    }


    override fun handleViewEvent(event: FavouriteFragContract.ViewEvent) {
        when (event) {
            FavouriteFragContract.ViewEvent.ServerErrorToast -> showErrorToast()
            FavouriteFragContract.ViewEvent.SubmitData -> showSubmitToast()
        }
    }


    private fun showErrorToast() {
        Toast.makeText(context!!, "Something happened worng", Toast.LENGTH_LONG).show()
    }

    private fun showSubmitToast() {
        Toast.makeText(context!!, "Submitting Data", Toast.LENGTH_LONG).show()
    }

    companion object{
        fun getInstance(): Fragment {
            return FavoriteFrag()
        }

    }
}