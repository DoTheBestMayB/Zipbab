package com.bestapp.zipbab.data.model.remote.user

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

internal data class NotificationResponse(
    val type: NotificationType = NotificationType.None,
    val createdAt: ZonedDateTime = defaultDateTime,
    val isRead: Boolean = false,
)
