package com.kodmap.deeplinktester.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kodmap.deeplinktester.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val platformModule = module {
    single { createDatabase() }
    single { get<AppDatabase>().appDao() }
    single { get<AppDatabase>().deeplinkDao() }
}

private fun createDatabase(): AppDatabase {
    val dbFilePath = NSHomeDirectory() + "/Documents/$DB_NAME"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
