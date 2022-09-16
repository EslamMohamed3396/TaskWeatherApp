package com.weatherapptask.network.weatherForcast.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Astro(
    @SerializedName("moon_illumination")
    val moonIllumination: String?,
    @SerializedName("moon_phase")
    val moonPhase: String?,
    @SerializedName("moonrise")
    val moonrise: String?,
    @SerializedName("moonset")
    val moonset: String?,
    @SerializedName("sunrise")
    val sunrise: String?,
    @SerializedName("sunset")
    val sunset: String?
) : Parcelable