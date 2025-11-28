package com.kodmap.deeplinktester.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kodmap.deeplinktester.data.entity.AppEntity
import com.kodmap.deeplinktester.data.entity.AppWithDeeplinks
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Transaction
    @Query("SELECT * FROM apps ORDER BY name ASC")
    fun getAllAppsWithDeeplinks(): Flow<List<AppWithDeeplinks>>

    @Query("SELECT * FROM apps WHERE id = :appId")
    suspend fun getAppById(appId: Long): AppEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: AppEntity): Long

    @Delete
    suspend fun deleteApp(app: AppEntity)
}
