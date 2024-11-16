package com.bestapp.zipbab.domain.model.user

import java.time.ZonedDateTime

data class Temperature(
    val num: Double,
    val recordedAt: ZonedDateTime,
)
