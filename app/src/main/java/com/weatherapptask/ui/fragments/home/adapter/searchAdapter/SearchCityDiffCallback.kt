package com.weatherapptask.ui.fragments.home.adapter.searchAdapter

import androidx.recyclerview.widget.DiffUtil
import com.weatherapptask.network.search.model.response.ResponseSearchItem


class SearchCityDiffCallback : DiffUtil.ItemCallback<ResponseSearchItem>() {
    override fun areItemsTheSame(
        oldItem: ResponseSearchItem,
        newItem: ResponseSearchItem
    ): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(
        oldItem: ResponseSearchItem,
        newItem: ResponseSearchItem
    ): Boolean {
        return oldItem == newItem
    }
}