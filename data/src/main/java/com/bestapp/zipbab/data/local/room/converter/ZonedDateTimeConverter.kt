package com.bestapp.zipbab.data.local.room.converter

import androidx.room.TypeConverter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeConverter {

    private val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    fun toZonedDateTime(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it, formatter) }
    }
}
