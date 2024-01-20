package com.gaur.himanshu

import android.app.Application
import initKoin
import org.koin.dsl.module

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            it.modules(
                module {
                    factory { this@BaseApplication.applicationContext }
                }
            )
        }

    }
}