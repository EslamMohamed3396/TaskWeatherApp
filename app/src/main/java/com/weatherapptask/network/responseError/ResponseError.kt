package com.weatherapptask.network.responseError


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseError(
    @SerializedName("error")
    val error: Error?
) : Parcelable