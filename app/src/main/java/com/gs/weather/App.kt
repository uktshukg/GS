package com.gs.weather

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.multidex.MultiDex
import com.gs.weather.services.NotificationService
import com.gs.weather.utilities.SharedPref
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector{


    companion object{
        var instance: App = App()
    }
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().applicationBind(this).build().inject(this)
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}