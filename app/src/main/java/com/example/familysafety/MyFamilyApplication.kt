package com.example.familysafety

import android.app.Application

class MyFamilyApplication : Application() {

        override fun onCreate() {
            super.onCreate()

            SharePref.init(this)
        }
    }