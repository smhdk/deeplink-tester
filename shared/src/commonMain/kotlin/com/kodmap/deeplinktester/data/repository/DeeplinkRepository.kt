package com.kodmap.deeplinktester.data.repository

import com.kodmap.deeplinktester.data.dao.AppDao
import com.kodmap.deeplinktester.data.dao.DeeplinkDao
import com.kodmap.deeplinktester.data.entity.AppEntity
import com.kodmap.deeplinktester.data.entity.AppWithDeeplinks
import com.kodmap.deeplinktester.data.entity.DeeplinkEntity
import kotlinx.coroutines.flow.Flow

class DeeplinkRepository(
    private val appDao: AppDao,
    private val deeplinkDao: DeeplinkDao
) {
    // App operations
    fun getAllAppsWithDeeplinks(): Flow<List<AppWithDeeplinks>> = 
        appDao.getAllAppsWithDeeplinks()

    suspend fun getAppById(appId: Long): AppEntity? = 
        appDao.getAppById(appId)

    suspend fun insertApp(name: String): Long = 
        appDao.insertApp(AppEntity(name = name))

    suspend fun deleteApp(app: AppEntity) = 
        appDao.deleteApp(app)

    // Deeplink operations
    fun getDeeplinksForApp(appId: Long): Flow<List<DeeplinkEntity>> = 
        deeplinkDao.getDeeplinksForApp(appId)

    suspend fun insertDeeplink(appId: Long, link: String): Long = 
        deeplinkDao.insertDeeplink(DeeplinkEntity(appId = appId, link = link))

    suspend fun updateDeeplink(deeplink: DeeplinkEntity) = 
        deeplinkDao.updateDeeplink(deeplink)

    suspend fun deleteDeeplink(deeplink: DeeplinkEntity) = 
        deeplinkDao.deleteDeeplink(deeplink)
}
