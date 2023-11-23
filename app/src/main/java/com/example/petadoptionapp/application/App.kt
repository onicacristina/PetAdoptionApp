package com.example.petadoptionapp.application

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        init()
    }

    private fun init() {
        initHawk()
        initTimber()
    }

    private fun initHawk() {
        Hawk.init(this).build()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}