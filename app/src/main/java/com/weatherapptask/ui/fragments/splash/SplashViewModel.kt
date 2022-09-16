package com.weatherapptask.ui.fragments.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.weatherapptask.ui.base.BaseViewModel
import com.weatherapptask.utilits.SingleLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    private val _goToHome = SingleLiveData<Unit>()
    val goToHome: LiveData<Unit> get() = _goToHome

    fun goToHome() {
        viewModelScope.launch {
            delay(3000)
            _goToHome.value = Unit
        }

    }

}