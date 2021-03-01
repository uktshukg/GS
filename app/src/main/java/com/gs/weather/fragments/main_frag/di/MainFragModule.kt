package com.gs.weather.fragments.main_frag.di

import android.content.Context
import com.gs.weather.api.ApiClientImpl
import com.gs.weather.api.IApiClient
import com.gs.base.Presenter
import com.gs.weather.fragments.main_frag.MainFrag
import com.gs.weather.fragments.main_frag.MainFragContract
import com.gs.weather.fragments.main_frag.MainFragPresenter
import com.gs.weather.store.IStore
import com.gs.weather.store.StoreImpl
import com.gs.weather.store.WeatherDataBase
import com.gs.weather.store.WeatherDataBaseDao
import com.gs.weather.utilities.createPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
abstract class MainFragModule {

    @Binds
    abstract fun server(server: ApiClientImpl): IApiClient

    @Binds
    abstract fun store(store: StoreImpl): IStore

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun initialState(): MainFragContract.State {
           return MainFragContract.State()
        }

        @Provides
        @JvmStatic
        fun database(context: Context): WeatherDataBase {
            return WeatherDataBase.getInstance(context)
        }

        @Provides
        @JvmStatic
        fun dao(database: WeatherDataBase): WeatherDataBaseDao {
            return database.weatherDataBaseDao()
        }



        @Provides
        @JvmStatic
        fun presenter(
            fragment: MainFrag,
            presenterProviderMain: Provider<MainFragPresenter>
        ): Presenter<MainFragContract.State, MainFragContract.ViewEvent> = fragment.createPresenter(presenterProviderMain)
    }
}
