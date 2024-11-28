package com.bestapp.zipbab.data.model.remote.user

import com.bestapp.zipbab.data.model.defaultDateTime
import java.time.ZonedDateTime

data class TemperatureResponse(
    val num: Double = DEFAULT_TEMPERATURE,
    val recordedAt: ZonedDateTime = defaultDateTime,
)

const val DEFAULT_TEMPERATURE = 36.5
