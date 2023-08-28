package com.weatherapptask.utilits

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import coil.load
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.convertDate(): String? {
    var result = ""
    //2022-09-16 20:46
    val formatterNew = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)

    var date: Date? = null
    try {
        date = formatDate().parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    if (date != null) {
        result = formatterNew.format(date)
    }
    return result
}


fun convertDateToNameOfDay(apiDate: String): String {
    var result = ""
    //2022-09-16 20:46

    val formatterNew = SimpleDateFormat("EEEE", Locale.ENGLISH)

    var date: Date? = null
    try {
        date = formatDate().parse(apiDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    if (date != null) {
        result = formatterNew.format(date)
    }
    return result
}

private fun getCurrentDate(): String {
    val currentDate = formatDate().format(Calendar.getInstance().time)
    return currentDate
}

fun whichDay(date: String): String {
    return if (getCurrentDate() == date) {
        "Today"
    } else if (differenceDates(date)) {
        "Tomorrow"
    } else {
        convertDateToNameOfDay(date)

    }
}

private fun differenceDates(apiDate: String): Boolean {

    val mDate11 = formatDate().parse(apiDate)
    val mDate22 = formatDate().parse(getCurrentDate())

    val mDifference = kotlin.math.abs(mDate11.time - mDate22.time)

    val mDifferenceDates = mDifference / (24 * 60 * 60 * 1000)

    val mDayDifference = mDifferenceDates
    if (mDayDifference.toInt() == 1) {
        return true
    }
    return false
}

private fun formatDate(): SimpleDateFormat {
    val mDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return mDateFormat
}

fun ImageView.loadImage(url: String?) {
    val newUrlAfterReplace = url?.replaceFirst("//", "")
    load("http://$newUrlAfterReplace")
}


fun ImageView.imageBitmap(base64: String?) {
    if (base64 != null) {
        val data = Base64.decode(base64, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(data, 0, data.size)
        load(decodedByte)
    }
}

