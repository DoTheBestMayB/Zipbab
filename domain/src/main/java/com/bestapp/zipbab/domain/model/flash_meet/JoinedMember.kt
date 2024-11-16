package com.bestapp.zipbab.domain.model.flash_meet

import com.bestapp.zipbab.domain.model.user.User
import java.time.ZonedDateTime

data class JoinedMember(
    val info: User,
    val joinedAt: ZonedDateTime,
)
