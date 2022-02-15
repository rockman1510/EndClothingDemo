package com.endclothingdemo.utils

import android.content.Context
import android.content.SharedPreferences
import com.endclothingdemo.BuildConfig

class SharePrefUtils(context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()


    fun putIsHoodies(isSave: Boolean) {
        editor.putBoolean(KEY_HOODIES, isSave).apply()
    }

    fun isHoodies() = sharedPreferences.getBoolean(KEY_HOODIES, true)

    fun putIsSneakers(isSave: Boolean) {
        editor.putBoolean(KEY_SNEAKERS, isSave).apply()
    }

    fun isSneakers() = sharedPreferences.getBoolean(KEY_SNEAKERS, true)

    companion object {
        private const val KEY_HOODIES = "KEY_HOODIES"
        private const val KEY_SNEAKERS = "KEY_SNEAKERS"
    }

}