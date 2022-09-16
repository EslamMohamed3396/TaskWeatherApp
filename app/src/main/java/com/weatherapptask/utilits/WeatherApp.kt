package com.weatherapptask.utilits

import android.app.Application
import com.weatherapptask.koin.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@WeatherApp)
            modules(appComponent)
        }
    }
}