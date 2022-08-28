package com.app.weatherapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {
    private const val PREFS_NAME = "APP_DATA"
    private lateinit var sharedPreferences:SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @SuppressLint("CommitPrefEdits")
    fun setLoginStatus(loginStatus: Boolean){
        sharedPreferences.edit().let{
            it.putBoolean("LOGIN_STATUS", loginStatus)
            it.apply()
        }
    }

    fun getLoginStatus(): Boolean{
        return sharedPreferences.getBoolean("LOGIN_STATUS", false)
    }

}