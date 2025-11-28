package com.kodmap.deeplinktester.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kodmap.deeplinktester.data.dao.AppDao
import com.kodmap.deeplinktester.data.dao.DeeplinkDao
import com.kodmap.deeplinktester.data.entity.AppEntity
import com.kodmap.deeplinktester.data.entity.DeeplinkEntity

@Database(
    entities = [AppEntity::class, DeeplinkEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun deeplinkDao(): DeeplinkDao
}
