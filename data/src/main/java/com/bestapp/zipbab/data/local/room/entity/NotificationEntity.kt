package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bestapp.zipbab.data.local.room.serializer.ZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

/**
 * @param type 알림 유형
 * @param createdAt 알림 생성 시간
 * @param isRead 알림 읽음 상태
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserPrivateEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["userId"])]
)
@Serializable
data class NotificationEntity(
    @PrimaryKey val documentId: String,
    val userId: String,
    val type: NotificationTypeEntity,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val createdAt: ZonedDateTime,
    val isRead: Boolean,
)

@Serializable
sealed interface NotificationTypeEntity {
    /**
     * @param meetId 모임 document ID
     * @param requestUserId 신청자 id
     * @param joinMessage 가입 인사말
     */
    @Serializable
    data class FlashMeetRequest(
        val meetId: String,
        val requestUserId: String,
        val joinMessage: String,
    ): NotificationTypeEntity

    @Serializable
    data class FlashMeetApproval(
        val meetId: String,
    ): NotificationTypeEntity

    @Serializable
    data class FlashMeetReject(
        val meetId: String,
        val reason: String,
    ): NotificationTypeEntity

    @Serializable
    data object None: NotificationTypeEntity
}
