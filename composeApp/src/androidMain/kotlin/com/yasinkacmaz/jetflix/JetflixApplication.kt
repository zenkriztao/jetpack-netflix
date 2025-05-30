package com.zenkriztao.netflix

import android.app.Application
import com.zenkriztao.netflix.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class JetflixApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@JetflixApplication)
        }
    }
}
