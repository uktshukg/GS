package com.gs.weather.fragments.main_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gs.base.BaseFragment
import com.gs.base.UserIntent
import com.gs.weather.R
import com.gs.weather.databinding.FragmentBlankBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainFrag :
    BaseFragment<MainFragContract.State, MainFragContract.ViewEvent, MainFragContract.Intent>(R.layout.fragment_blank) {
    private var isFavourite: Boolean = false
    private lateinit var weatherListAdapter: WeatherListAdapter
    private lateinit var weatherDetailsList: RecyclerView
    private lateinit var edTextCityName: EditText
    private lateinit var btnSearch: Button
    private var getCityData: PublishSubject<String?> = PublishSubject.create()
    private var addFavouriteSubject: PublishSubject<Pair<Int, Boolean>> = PublishSubject.create()
    private var completeSession: PublishSubject<Pair<String, Float>> = PublishSubject.create()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSearch = view.findViewById(R.id.btn_search)
        edTextCityName = view.findViewById(R.id.et_search_term)
        btnSearch.setOnClickListener {
            getCityData.onNext(edTextCityName.text.toString())
        }
        weatherDetailsList = view.findViewById(R.id.list)
        weatherListAdapter = WeatherListAdapter()
        weatherDetailsList.adapter = weatherListAdapter
        binding.favourite.setOnClickListener {
            getCurrentState().localCityData?.let {
                addFavouriteSubject.onNext(it.id to !isFavourite)
            }
        }
        binding.goToFavourite.setOnClickListener {
            findNavController().navigate(R.id.favFrag)
        }
    }

    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun userIntents(): Observable<UserIntent> {
        return Observable.mergeArray(
            Observable.just(MainFragContract.Intent.Load),

            getCityData.map {
                MainFragContract.Intent.GetCityData(it)
            },
            completeSession.map {
                MainFragContract.Intent.CompleteSession(it)
            },
            addFavouriteSubject.map {
                MainFragContract.Intent.AddFavourite(it)
            }
        )
    }


    override fun render(state: MainFragContract.State) {
        state.localCityData?.let {
            weatherListAdapter.submitList(it.weather)
            binding.latitudeTv.text = "Latitude ${it.coord.lat}"
            binding.longitudeTv.text = "Longitude ${it.coord.lon}"
            binding.temp.text = "Temp ${it.main.temp}"
            binding.feels.text = "Feels ${it.main.feels_like}"
            binding.min.text = "Min Temp ${it.main.temp_min}"
            binding.max.text = "Max Temp ${it.main.temp_max}"
            binding.pressure.text = "Pressure ${it.main.pressure}"
            binding.humidity.text = "Humidity ${it.main.humidity}"
            binding.speed.text = "Wind  Speed ${it.wind.speed}"
            binding.deg.text = "Wind degree ${it.wind.deg}"
            binding.cloudInfo.text = "Clouds Info: ${it.clouds.all}"
            binding.date.text = "Date: ${it.readableDate}"
            binding.sysSunrise.text = "Sunrise  ${it.sys.sunrise}"
            binding.sysSunset.text = "Sunset  ${it.sys.sunset}"
            binding.name.text = "Place Name ${it.name}"
            if (state.canSetFavourite) {
                isFavourite = true
                binding.favourite.text = getString(R.string.remove_fav)
            } else {
                isFavourite = false
                binding.favourite.text = getString(R.string.add_fav)
            }

        }
        if (state.loading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }


    override fun handleViewEvent(event: MainFragContract.ViewEvent) {
        when (event) {
            MainFragContract.ViewEvent.ErrorToast -> {
                Toast.makeText(requireContext(), "Something wrong happened!!", Toast.LENGTH_LONG)
                    .show()
            }
            MainFragContract.ViewEvent.NoDataFound -> Toast.makeText(
                requireContext(),
                "No Data found",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}