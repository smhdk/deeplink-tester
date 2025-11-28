package com.kodmap.deeplinktester

import android.app.Application
import com.kodmap.deeplinktester.di.platformModule
import com.kodmap.deeplinktester.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DeeplinkTesterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@DeeplinkTesterApp)
            modules(platformModule, sharedModule)
        }
    }
}
