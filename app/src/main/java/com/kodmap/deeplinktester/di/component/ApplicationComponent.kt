package com.kodmap.deeplinktester.di.component

import android.content.Context
import android.content.SharedPreferences
import com.kodmap.deeplinktester.App
import com.kodmap.deeplinktester.di.module.ApplicationModule
import com.kodmap.deeplinktester.di.module.DatabaseModule
import com.kodmap.deeplinktester.ui.deeplink.DeeplinkActivityViewModel
import com.kodmap.deeplinktester.ui.main.MainActivity
import com.kodmap.deeplinktester.ui.main.MainActivityViewModel

import dagger.Component
import javax.inject.Singleton


@Singleton


@Component(modules = arrayOf(ApplicationModule::class,DatabaseModule::class))

interface ApplicationComponent {
    fun app(): App


    fun context(): Context

    fun preferences(): SharedPreferences

    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(mainActivityViewModel: MainActivity)
    fun inject(deeplinkActivityViewModel: DeeplinkActivityViewModel)
}
