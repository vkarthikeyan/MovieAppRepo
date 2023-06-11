package com.example.movieapp

import android.app.Application
import android.content.Context

class MovieApplication: Application() {
    lateinit var context: Context

    companion object {
        fun getAppContext(): MovieApplication {
            return getAppContext()
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}