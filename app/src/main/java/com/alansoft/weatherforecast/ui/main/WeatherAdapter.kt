package com.alansoft.weatherforecast.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.weatherforecast.R
import com.alansoft.weatherforecast.data.response.SealedViewHolderData
import com.alansoft.weatherforecast.databinding.ItemWeatherBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@FragmentScoped
class WeatherAdapter @Inject constructor() :
    ListAdapter<SealedViewHolderData, ViewHolder>(
        AsyncDifferConfig.Builder(DiffCallback()).build()
    ) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SealedViewHolderData.WeatherItem -> R.layout.item_weather
            is SealedViewHolderData.HeaderItem -> R.layout.item_header
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currentList.size > position) {
            val item = getItem(position)
            holder.binding?.run {
                setVariable(BR.item, item)
                executePendingBindings()
            }
        }
    }

}

class ViewHolder(parent: ViewGroup, @LayoutRes resource: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(resource, parent, false)
) {
    var binding: ItemWeatherBinding? = try {
        DataBindingUtil.bind(itemView)
    } catch (e: Exception) {
        null
    }
}

private class DiffCallback : DiffUtil.ItemCallback<SealedViewHolderData>() {
    override fun areItemsTheSame(
        oldItem: SealedViewHolderData,
        newItem: SealedViewHolderData
    ): Boolean {
        return when {
            oldItem is SealedViewHolderData.HeaderItem
                    && newItem is SealedViewHolderData.HeaderItem -> oldItem.text == newItem.text
            oldItem is SealedViewHolderData.WeatherItem
                    && newItem is SealedViewHolderData.WeatherItem -> oldItem.weather.size == newItem.weather.size
                    && oldItem.woeId == newItem.woeId
                    && oldItem.location == newItem.location
                    && (!oldItem.weather.isNullOrEmpty() && !newItem.weather.isNullOrEmpty()
                    && oldItem.weather.size > 1 && newItem.weather.size > 1
                    && oldItem.weather[0].weatherStateName == newItem.weather[0].weatherStateName
                    && oldItem.weather[1].weatherStateName == newItem.weather[1].weatherStateName
                    && oldItem.weather[0].getTheTempString() == newItem.weather[0].getTheTempString()
                    && oldItem.weather[1].getTheTempString() == newItem.weather[1].getTheTempString()
                    && oldItem.weather[0].getHumidityString() == newItem.weather[0].getHumidityString()
                    && oldItem.weather[1].getHumidityString() == newItem.weather[1].getHumidityString())
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: SealedViewHolderData,
        newItem: SealedViewHolderData
    ): Boolean {
        return oldItem == newItem
    }
}