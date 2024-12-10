package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.user.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    val notification: Flow<List<Notification>>
}
