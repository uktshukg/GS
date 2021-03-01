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
import com.gs.weather.fragments.main_frag.MainFragContract
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


class FavoriteFrag :
    BaseFragment<FavouriteFragContract.State, FavouriteFragContract.ViewEvent, FavouriteFragContract.Intent>(
        R.layout.favourite_frag
    ) {
    private lateinit var adpater: FavouriteAdapter
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
            Observable.just(FavouriteFragContract.Intent.Load)
        )
    }

    override fun render(state: FavouriteFragContract.State) {
        adpater.submitList(state.list)
    }


    override fun handleViewEvent(event: FavouriteFragContract.ViewEvent) {
        when (event) {
            FavouriteFragContract.ViewEvent.ErrorToast -> {
                Toast.makeText(requireContext(), "Something wrong happened!!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


}