package com.gs.weather.fragments.favourite_frag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gs.weather.R


class FavouriteAdapter :
    ListAdapter<String, FavouriteAdapter.FavouriteViewHolder>(FavouriteDiffUtil()) {
    class FavouriteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.label)
        fun bind(item: String?) {
            item?.let {
                name.text = it
            }
        }
    }

    class FavouriteDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
