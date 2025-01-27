package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationLocalDataSource {

    val notifications: Flow<List<NotificationEntity>>
}
