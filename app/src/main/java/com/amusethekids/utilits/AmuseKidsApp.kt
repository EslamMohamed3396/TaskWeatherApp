package com.amusethekids.utilits

import android.app.Application
import com.amusethekids.koin.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module


class AmuseKidsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@AmuseKidsApp)
            modules(createModules())
        }
    }

    fun createModules(): List<Module> {
        return appComponent
    }
}