package com.kodmap.deeplinktester.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kodmap.deeplinktester.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val platformModule = module {
    single { createDatabase(get()) }
    single { get<AppDatabase>().appDao() }
    single { get<AppDatabase>().deeplinkDao() }
}

private fun createDatabase(context: Context): AppDatabase {
    val dbFile = context.getDatabasePath(DB_NAME)
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
