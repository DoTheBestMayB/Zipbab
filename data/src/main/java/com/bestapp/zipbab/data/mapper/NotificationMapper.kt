package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.local.room.entity.NotificationEntity
import com.bestapp.zipbab.data.local.room.entity.NotificationTypeEntity
import com.bestapp.zipbab.domain.model.user.Notification
import com.bestapp.zipbab.domain.model.user.NotificationType

fun NotificationEntity.toDomain(): Notification {
    return Notification(
        uuid = documentId,
        type = when (type) {
            is NotificationTypeEntity.FlashMeetRequest -> NotificationType.FlashMeetRequest(
                meetId = type.meetId,
                requestUserId = type.requestUserId,
                joinMessage = type.joinMessage,
            )
            is NotificationTypeEntity.FlashMeetApproval -> NotificationType.FlashMeetApproval(
                meetId = type.meetId
            )
            is NotificationTypeEntity.FlashMeetReject -> NotificationType.FlashMeetReject(
                meetId = type.meetId, reason = type.reason
            )
            NotificationTypeEntity.None -> NotificationType.None
        },
        createdAt = createdAt,
        isRead = isRead,
    )
}

fun Notification.toEntity(): NotificationEntity {
    return NotificationEntity(
        documentId = uuid,
        type = when (val type = type) {
            is NotificationType.FlashMeetApproval -> NotificationTypeEntity.FlashMeetApproval(meetId = type.meetId)
            is NotificationType.FlashMeetReject -> NotificationTypeEntity.FlashMeetReject(
                meetId = type.meetId,
                reason = type.reason,
            )
            is NotificationType.FlashMeetRequest -> NotificationTypeEntity.FlashMeetRequest(
                meetId = type.meetId,
                requestUserId = type.requestUserId,
                joinMessage = type.joinMessage,
            )
            NotificationType.None -> NotificationTypeEntity.None
        },
        createdAt = createdAt,
        isRead = isRead
    )
}
