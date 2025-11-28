package com.kodmap.deeplinktester.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kodmap.deeplinktester.data.entity.DeeplinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeeplinkDao {
    @Query("SELECT * FROM deeplinks WHERE appId = :appId ORDER BY id DESC")
    fun getDeeplinksForApp(appId: Long): Flow<List<DeeplinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeeplink(deeplink: DeeplinkEntity): Long

    @Update
    suspend fun updateDeeplink(deeplink: DeeplinkEntity)

    @Delete
    suspend fun deleteDeeplink(deeplink: DeeplinkEntity)

    @Query("DELETE FROM deeplinks WHERE appId = :appId")
    suspend fun deleteAllDeeplinksForApp(appId: Long)
}
