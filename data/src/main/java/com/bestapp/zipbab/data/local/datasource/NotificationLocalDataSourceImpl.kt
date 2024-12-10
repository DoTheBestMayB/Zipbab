package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.dao.NotificationDao
import com.bestapp.zipbab.data.local.room.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationLocalDataSourceImpl @Inject constructor(
    private val notificationDao: NotificationDao,
) : NotificationLocalDataSource {

    override val notifications: Flow<List<NotificationEntity>> = notificationDao.getNotification()
}
