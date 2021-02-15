package com.gs.weather

import android.content.Context
import com.gs.weather.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {
    @Module
  companion object{
        @JvmStatic
        @Provides
        @AppScope
        fun applicationContext(app: App): Context = app
    }
}