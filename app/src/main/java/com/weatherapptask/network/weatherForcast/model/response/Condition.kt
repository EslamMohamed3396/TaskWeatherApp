package com.weatherapptask.network.weatherForcast.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condition(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("text")
    val text: String?
) : Parcelable