package com.gs.weather.fragments.main_frag.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gs.weather.R
import com.gs.weather.fragments.main_frag.models.Weather

class WeatherViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val description = view.findViewById<TextView>(R.id.description)
    fun bind(item: Weather?) {
        item?.let {
            description.text = it.description
        }
    }

}
