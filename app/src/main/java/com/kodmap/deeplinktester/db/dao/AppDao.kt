package com.kodmap.deeplinktester.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kodmap.deeplinktester.db.entities.AppEntity
import com.kodmap.deeplinktester.db.entities.AppItem

@Dao
interface AppDao{
    @Query("SELECT * FROM App")
    fun getAppItems(): LiveData<List<AppItem>>

    @Query("SELECT * FROM App where id = :appId")
    fun getAppItem(appId: Int): AppItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApp(app: AppEntity)

    @Delete
    fun deleteApp(example: AppEntity)
}