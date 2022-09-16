package com.weatherapptask.network.weatherForcast.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    @SerializedName("country")
    val country: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("localtime")
    val localtime: String?,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("tz_id")
    val tzId: String?
) : Parcelable