package com.weatherapptask.ui.fragments.home.adapter.weatherCastAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

import com.weatherapptask.databinding.ItemWeatherForThreeDaysBinding
import com.weatherapptask.network.weatherForcast.model.response.Forecastday
import com.weatherapptask.ui.base.BaseViewHolder
import com.weatherapptask.utilits.Constant
import com.weatherapptask.utilits.loadImage
import com.weatherapptask.utilits.whichDay


class WeatherCastAdapter :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, WeatherCastDiffCallback())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemWeatherForThreeDaysBinding =
            ItemWeatherForThreeDaysBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemWeatherCastViewHolder(
            itemWeatherForThreeDaysBinding
        )

    }

    fun clickOnConvertUnit() {
        notifyDataSetChanged()
    }

    fun submitList(data: List<Forecastday?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as ItemWeatherCastViewHolder).bind(item)
    }

    inner class ItemWeatherCastViewHolder(val binding: ItemWeatherForThreeDaysBinding) :
        BaseViewHolder<Forecastday>(binding) {
        override fun bind(item: Forecastday) {
            item.isFTemp = Constant.CONVERT_UNIT.IS_FAHRENHEIT_TEMPERATURE
            when (item.isFTemp) {
                true -> {
                    binding.tvDegree.text = "${item.day?.mintempF}°/${item.day?.maxtempF}°F"
                }
                false -> {
                    binding.tvDegree.text = "${item.day?.mintempC}°/${item.day?.maxtempC}°C"
                }
            }

            binding.imIcon.loadImage(item.day?.condition?.icon)

            binding.tvDate.text = whichDay(item.date!!)
        }
    }

}