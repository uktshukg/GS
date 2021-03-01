package com.gs.weather.fragments.main_frag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.gs.weather.R
import com.gs.weather.fragments.main_frag.models.Weather

class WeatherListAdapter : ListAdapter<Weather, WeatherViewHolder>(WeatherUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
