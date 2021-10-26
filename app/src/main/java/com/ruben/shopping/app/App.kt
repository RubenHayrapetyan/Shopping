package com.ruben.shopping.app

import android.app.Application
import com.ruben.shopping.di.appRepo
import com.ruben.shopping.di.appViewModels
import com.ruben.shopping.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf( appRepo, appViewModels, networkModule))
        }
    }
}