package com.gs.weather.fragments.favourite_frag.di

import android.content.Context
import com.gs.base.Presenter
import com.gs.weather.api.ApiClientImpl
import com.gs.weather.api.IApiClient
import com.gs.weather.fragments.favourite_frag.FavoriteFrag
import com.gs.weather.fragments.favourite_frag.FavouriteFragContract
import com.gs.weather.fragments.favourite_frag.FavouriteFragPresenter
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
abstract class FavouriteFragModule {

    @Binds
    abstract fun server(server: ApiClientImpl): IApiClient

    @Binds
    abstract fun store(store: StoreImpl): IStore

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun initialState(): FavouriteFragContract.State {
            return FavouriteFragContract.State()
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
            fragment: FavoriteFrag,
            presenterProviderMain: Provider<FavouriteFragPresenter>
        ): Presenter<FavouriteFragContract.State, FavouriteFragContract.ViewEvent> =
            fragment.createPresenter(presenterProviderMain)
    }
}
