package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.local.datasource.NotificationLocalDataSource
import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.domain.model.user.Notification
import com.bestapp.zipbab.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationLocalDataSource: NotificationLocalDataSource,
) : NotificationRepository {

    override val notification: Flow<List<Notification>> =
        notificationLocalDataSource.notifications.map { notifications ->
            notifications.map {
                it.toDomain()
            }
        }
}
