package com.weatherapptask.utilits

import android.content.SharedPreferences
import com.google.gson.Gson


class UserPreferences(
    private val securePrefs: SharedPreferences,
    private val gson: Gson
) {
    companion object {
        const val SHARED_PREFS_NAME = "com.amuse.kids.user"
        const val USER_KEY = "USER_KEY"
        const val AUTH0_TOKEN = "AUTH0_TOKEN"
        const val TOPICS_LIST = "topics_list"

    }

//    var currentUser: CurrentUser?
//        get() {
//            val userJson = getString(USER_KEY)
//            return userJson?.let {
//                gson.fromJson(it, CurrentUser::class.java)
//            }
//        }
//        set(user) {
//            putString(USER_KEY, gson.toJson(user))
//        }


    fun putBool(key: String, value: Boolean) {
        securePrefs.edit().putBoolean(key, value).apply()
    }

    fun getBool(key: String, defVal: Boolean = false) = securePrefs.getBoolean(key, defVal)

    fun putString(key: String, value: String?) {
        securePrefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defVal: String? = null) = securePrefs.getString(key, defVal)


    fun setLoggedOut() {
        securePrefs.edit().clear().apply()
    }
}
