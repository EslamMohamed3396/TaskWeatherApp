package com.weatherapptask.network.weatherForcast.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>?
) : Parcelable