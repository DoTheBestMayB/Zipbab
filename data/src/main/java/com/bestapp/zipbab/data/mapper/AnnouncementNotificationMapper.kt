package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.notice.AnnouncementNotificationResponse
import com.bestapp.zipbab.domain.model.notice.AnnouncementNotification
import com.bestapp.zipbab.domain.model.notice.AnnouncementNotificationType

fun AnnouncementNotificationResponse.toDomain(): AnnouncementNotification {
    return AnnouncementNotification(
        displayText = displayText,
        eventId = eventId,
        type = type.toDomainNotificationType(),
    )
}

private fun String.toDomainNotificationType(): AnnouncementNotificationType {
    return AnnouncementNotificationType.entries.firstOrNull { it.name == this } ?: AnnouncementNotificationType.NONE
}
