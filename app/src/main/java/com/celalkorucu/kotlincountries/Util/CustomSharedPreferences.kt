package com.celalkorucu.kotlincountries.Util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPreferences {



    companion object {
        private var sharedPreferences : SharedPreferences? = null
        private val PREFERENCES_TIME = "preferences_time"

        @Volatile private var instance : CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: createSharedPreferences(context).also {

                instance = it
            }
        }

        private fun createSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }



    }

    fun saveTime( time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME , time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)


}