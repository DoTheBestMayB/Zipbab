package com.bestapp.zipbab.data.local.room.converter

import androidx.room.TypeConverter
import com.bestapp.zipbab.data.local.room.entity.NotificationTypeEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NotificationTypeConverter {

    private val json = Json

    @TypeConverter
    fun fromNotificationType(value: NotificationTypeEntity): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toNotificationType(value: String): NotificationTypeEntity {
        return json.decodeFromString(value)
    }
}
