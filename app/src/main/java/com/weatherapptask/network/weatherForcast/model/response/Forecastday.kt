package com.weatherapptask.network.weatherForcast.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecastday(
    @SerializedName("astro")
    val astro: Astro?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("date_epoch")
    val dateEpoch: Int?,
    @SerializedName("day")
    val day: Day?,
    @SerializedName("hour")
    val hour: List<Hour>?,

    var isFTemp: Boolean = true
) : Parcelable