package com.bestapp.zipbab.data.model.remote.flash_meet

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

data class JoinedMemberResponse(
    val memberId: String = "",
    val joinedAt: ZonedDateTime = defaultDateTime,
)
