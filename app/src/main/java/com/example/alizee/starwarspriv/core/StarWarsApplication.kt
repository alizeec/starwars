package com.example.alizee.starwarspriv.core

import android.app.Application
import com.example.alizee.starwarspriv.core.di.AppComponent
import com.example.alizee.starwarspriv.core.di.AppModule
import com.example.alizee.starwarspriv.core.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class StarWarsApplication  : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        setup()
    }

    private fun setup() {
        Stetho.initializeWithDefaults(this)

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
        instance = this
    }

    fun getComponent(): AppComponent = component

    companion object {
        lateinit var instance: StarWarsApplication private set
    }
}