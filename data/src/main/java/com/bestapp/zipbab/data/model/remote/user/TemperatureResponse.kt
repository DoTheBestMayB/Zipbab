package com.bestapp.zipbab.data.model.remote.user

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

data class TemperatureResponse(
    val num: Double = 0.0,
    val recordedAt: ZonedDateTime = defaultDateTime,
)
