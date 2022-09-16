package com.weatherapptask.ui.fragments.splash

import androidx.lifecycle.LiveData
import com.weatherapptask.ui.base.BaseViewModel
import com.weatherapptask.utilits.SingleLiveData
import com.weatherapptask.utilits.UserPreferences

class SplashViewModel(val sharedPreferences: UserPreferences) : BaseViewModel() {

    private val _isUserSignIn = SingleLiveData<Unit>()
    val isUserSignIn: LiveData<Unit> get() = _isUserSignIn


    private val _isUserNotLogin = SingleLiveData<Unit>()
    val isUserNotLogin: LiveData<Unit> get() = _isUserNotLogin


    fun isUserSignIn() {
        if (!sharedPreferences.getBool("")) return
        _isUserSignIn.postValue(Unit)
    }

    fun isUserNotLogin() {
        if (!sharedPreferences.getBool("")) return
        _isUserNotLogin.postValue(Unit)
    }

}