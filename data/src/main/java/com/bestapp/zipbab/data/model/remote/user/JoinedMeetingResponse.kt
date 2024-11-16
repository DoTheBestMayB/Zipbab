package com.bestapp.zipbab.data.model.remote.user

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

internal data class JoinedMeetingResponse(
    val meetId: String = "",
    val joinedAt: ZonedDateTime = defaultDateTime,
    val type: MeetType = MeetType.FLASH_MEET,
    val hasReviewed: Boolean = false,
    val reviewId: String = "",
)

internal enum class MeetType {
    FLASH_MEET, MEET
}
