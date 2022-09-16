package com.weatherapptask.utilits

import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import coil.load
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
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


fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, start: Int, before: Int, count: Int ->

            if (count > 0 && text.toString().isNotEmpty()) {
                trySend(text)
            }
        }
        awaitClose { removeTextChangedListener(listener) }
    }.onCompletion { emit(text) }
}

