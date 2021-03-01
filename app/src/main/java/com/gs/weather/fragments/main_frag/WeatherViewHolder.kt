package com.gs.weather.fragments.main_frag

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gs.weather.R
import com.gs.weather.fragments.main_frag.models.Weather

class WeatherViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val main = view.findViewById<TextView>(R.id.main)
    val description = view.findViewById<TextView>(R.id.description)
    fun bind(item: Weather?) {
        item?.let {
            main.text = it.main
            description.text = it.description
        }
    }

}
