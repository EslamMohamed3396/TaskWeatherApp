package com.weatherapptask.ui.fragments.home.adapter.weatherCastAdapter

import androidx.recyclerview.widget.DiffUtil
import com.weatherapptask.network.weatherForcast.model.response.Forecastday


class WeatherCastDiffCallback : DiffUtil.ItemCallback<Forecastday>() {
    override fun areItemsTheSame(
        oldItem: Forecastday,
        newItem: Forecastday
    ): Boolean {
        return oldItem.date == newItem.date

    }

    override fun areContentsTheSame(
        oldItem: Forecastday,
        newItem: Forecastday
    ): Boolean {
        return oldItem == newItem
    }
}