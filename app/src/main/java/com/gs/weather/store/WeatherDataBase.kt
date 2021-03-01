package com.gs.weather.store

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    version = WeatherDataBase.DB_VERSION,
    entities = [LocalCityData::class],
)
@TypeConverters(WeatherTypeConvertor::class)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun weatherDataBaseDao(): WeatherDataBaseDao

    companion object {
        const val DB_VERSION = 1
        internal const val DB_NAME ="weather.db"

        internal var INSTANCE: WeatherDataBase? = null

        fun getInstance(context: Context): WeatherDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, WeatherDataBase::class.java, DB_NAME)
                    .build()
            }
            return INSTANCE!!
        }


    }

}


