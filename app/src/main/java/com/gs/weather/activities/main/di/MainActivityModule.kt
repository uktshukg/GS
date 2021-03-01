package com.gs.weather.activities.main.di

import com.gs.weather.fragments.favourite_frag.FavoriteFrag
import com.gs.weather.fragments.favourite_frag.di.FavouriteFragModule
import com.gs.weather.fragments.main_frag.MainFrag
import com.gs.weather.fragments.main_frag.di.MainFragModule
import com.gs.weather.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

       /****************************************************************
         * Fragments
         ****************************************************************/
        @FragmentScope
        @ContributesAndroidInjector(modules = [MainFragModule::class])
        abstract fun fragOne(): MainFrag

    @FragmentScope
    @ContributesAndroidInjector(modules = [FavouriteFragModule::class])
    abstract fun favouriteFragOne(): FavoriteFrag
    }


