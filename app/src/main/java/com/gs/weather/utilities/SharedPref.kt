package com.gs.weather.utilities

import android.content.Context
import android.content.SharedPreferences

object SharedPref {


    const val PREF_NAME = "GS_REPO_FILE"
    fun saveData(key: String, value: String, context: Context) {
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(key, "0")
    }

    object KEYS{
        const val LAST_UPDATED_TIME = "last_updated_time_in_mills"
    }
}