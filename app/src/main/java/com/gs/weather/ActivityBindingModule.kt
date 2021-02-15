package com.gs.weather

import com.gs.weather.activities.main.MainActivity
import com.gs.weather.activities.main.di.MainActivityModule
import com.gs.weather.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}