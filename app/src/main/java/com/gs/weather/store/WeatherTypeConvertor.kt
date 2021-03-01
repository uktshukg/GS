package com.gs.weather.store

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gs.weather.fragments.main_frag.models.Weather
import java.lang.reflect.Type
import java.util.*


class WeatherTypeConvertor {

            var gson = Gson()
            @TypeConverter
            fun stringToSomeObjectList(data: String?): List<Weather> {
                if (data == null) {
                    return Collections.emptyList()
                }
                val listType: Type = object : TypeToken<List<Weather?>?>() {}.getType()
                return gson.fromJson<List<Weather>>(data, listType)
            }

            @TypeConverter
            fun someObjectListToString(someObjects: List<Weather?>?): String {
                return gson.toJson(someObjects)
            }
        }



