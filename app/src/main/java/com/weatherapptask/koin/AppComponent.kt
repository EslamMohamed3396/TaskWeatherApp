package com.weatherapptask.koin


import com.weatherapptask.ui.fragments.home.HomeViewModel
import com.weatherapptask.ui.fragments.splash.SplashViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// setup Koin


val SplashViewModel = module {
    viewModel { SplashViewModel() }
}

val HomeViewModel = module {
    viewModel { HomeViewModel(get()) }
}

val appComponent = module {
    val component = mutableListOf(
        SplashViewModel,
        HomeViewModel
    )
    component.addAll(networkComponent)
    includes(component)

}