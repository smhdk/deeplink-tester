package com.kodmap.deeplinktester

import com.kodmap.deeplinktester.di.component.DaggerApplicationComponent
import com.kodmap.deeplinktester.di.module.ApplicationModule

class App : android.app.Application() {

    val component by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

