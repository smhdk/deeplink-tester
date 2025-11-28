package com.kodmap.deeplinktester.di

import com.kodmap.deeplinktester.data.AppDatabase
import org.koin.core.module.Module

expect val platformModule: Module

internal const val DB_NAME = "deeplink_tester.db"
