package com.example.gibranlyra.amarotest

import android.app.Application
import com.example.amaroservice.AmaroApiModule
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
class AppContext : Application() {

    companion object {
        lateinit var instance: AppContext private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeTimezone()
        initializeTimber()
        initializeApiModules()
    }

    private fun initializeTimezone() {
        AndroidThreeTen.init(this)
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeApiModules() {
        //Initialize ApiModule Singleton
        AmaroApiModule.setRetrofit()
    }
}