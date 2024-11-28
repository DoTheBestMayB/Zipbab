package com.bestapp.zipbab.data.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * @param date yyyy-MM-dd 형태의 날짜
 * @param hour 시간
 * @param minute 분
 * @param timeZone 타임존
 */
fun createZonedDateTime(date: String, hour: Int, minute: Int, timeZone: String = "UTC"): ZonedDateTime {
    val localDate = LocalDate.parse(date)
    val localTime = LocalTime.of(hour, minute)
    val localDateTime = LocalDateTime.of(localDate, localTime)
    val zoneId = ZoneId.of(timeZone)
    return ZonedDateTime.of(localDateTime, zoneId)
}
