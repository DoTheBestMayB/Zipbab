package com.bestapp.zipbab.domain.model.user

import java.time.ZonedDateTime

data class Notification(
    val uuid: String,
    val userId: String,
    val type: NotificationType,
    val createdAt: ZonedDateTime,
    val isRead: Boolean,
)
