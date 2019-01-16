package com.kodmap.deeplinktester.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kodmap.deeplinktester.db.entities.AppEntity
import com.kodmap.deeplinktester.db.entities.AppItem
import com.kodmap.deeplinktester.db.entities.DeeplinkEntity

@Dao
interface DeeplinkDao{
    @Query("SELECT * FROM Deeplink where appId = :appId")
    fun getLinksFromApp(appId: Int): LiveData<List<DeeplinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeeplink(deeplinkEntity: DeeplinkEntity)

    @Delete
    fun deleteDeeplink(deeplinkEntity: DeeplinkEntity)

    @Delete
    fun deleteDeeplinks(deeplinkList : List<DeeplinkEntity>)
}
