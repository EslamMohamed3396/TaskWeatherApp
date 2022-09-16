package com.weatherapptask.koin


import com.weatherapptask.ui.fragments.splash.SplashViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// setup Koin


val SplashViewModel = module {
    viewModel { SplashViewModel() }
}

val appComponent = module {
    val component = mutableListOf(
        SplashViewModel
    )
    component.addAll(networkComponent)
    includes(component)

}