package com.kodmap.deeplinktester.di.module

import androidx.room.Room
import android.content.Context
import com.kodmap.deeplinktester.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context,
                AppDatabase::class.java, "deeplink-tester-db")
                .fallbackToDestructiveMigration()
                .build()
    }

}