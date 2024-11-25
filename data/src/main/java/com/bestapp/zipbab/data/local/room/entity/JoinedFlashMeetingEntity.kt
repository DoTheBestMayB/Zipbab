package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity
data class JoinedFlashMeetingEntity(
    @PrimaryKey val meetId: String,
    val joinedAt: ZonedDateTime,
    val type: MeetTypeEntity,
    val hasReviewed: Boolean,
    val reviewId: String,
)

enum class MeetTypeEntity {
    FLASH_MEET, MEET
}
