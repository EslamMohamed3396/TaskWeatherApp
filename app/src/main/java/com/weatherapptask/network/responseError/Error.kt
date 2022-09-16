package com.weatherapptask.network.responseError


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Error(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?
) : Parcelable