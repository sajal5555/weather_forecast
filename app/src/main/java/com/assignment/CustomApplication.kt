package com.assignment

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class CustomApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: CustomApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context =
            applicationContext()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }
}