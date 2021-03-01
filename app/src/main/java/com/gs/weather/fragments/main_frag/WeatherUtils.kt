package com.gs.weather.fragments.main_frag

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.gs.weather.fragments.main_frag.models.Weather

class WeatherUtils : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
     return oldItem.id ==newItem.id
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem==newItem
    }


}
