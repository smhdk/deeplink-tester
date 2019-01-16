package com.kodmap.deeplinktester.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kodmap.deeplinktester.db.dao.AppDao
import com.kodmap.deeplinktester.db.dao.DeeplinkDao
import com.kodmap.deeplinktester.db.entities.AppEntity
import com.kodmap.deeplinktester.db.entities.DeeplinkEntity

@Database(entities = [DeeplinkEntity::class, AppEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deeplinkDao() : DeeplinkDao
    abstract fun appDao() : AppDao
}