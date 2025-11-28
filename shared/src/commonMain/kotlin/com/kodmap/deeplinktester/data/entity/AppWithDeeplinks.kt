package com.kodmap.deeplinktester.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AppWithDeeplinks(
    @Embedded
    val app: AppEntity,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "appId"
    )
    val deeplinks: List<DeeplinkEntity>
) {
    val deeplinkCount: Int
        get() = deeplinks.size
}
