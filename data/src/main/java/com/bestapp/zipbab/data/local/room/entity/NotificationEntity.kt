package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

/**
 * @param type 알림 유형
 * @param createdAt 알림 생성 시간
 * @param isRead 알림 읽음 상태
 */
@Entity
data class NotificationEntity(
    @PrimaryKey val documentId: String,
    val type: NotificationTypeEntity,
    val createdAt: ZonedDateTime,
    val isRead: Boolean,
)


sealed interface NotificationTypeEntity {
    /**
     * @param meetId 모임 document ID
     * @param requestUserId 신청자 id
     * @param joinMessage 가입 인사말
     */
    data class FlashMeetRequest(
        val meetId: String,
        val requestUserId: String,
        val joinMessage: String,
    ): NotificationTypeEntity

    data class FlashMeetApproval(
        val meetId: String,
    ): NotificationTypeEntity

    data class FlashMeetReject(
        val meetId: String,
        val reason: String,
    ): NotificationTypeEntity

    data object None: NotificationTypeEntity
}
