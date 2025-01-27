package com.bestapp.zipbab.domain.model.user

import java.time.ZonedDateTime

data class JoinedFlashMeeting(
    val meetId: String,
    val userId: String,
    val joinedAt: ZonedDateTime,
    val type: MeetType,
    val hasReviewed: Boolean,
    val reviewId: String,
)

enum class MeetType(val symbol: String) {
    FLASH_MEET("flashMeet"), MEET("meet"), NOT_SUPPORTED("none")
}
