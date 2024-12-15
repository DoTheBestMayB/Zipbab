package com.bestapp.zipbab.data.local.room.converter

import androidx.room.TypeConverter
import com.bestapp.zipbab.data.local.room.entity.Icon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IconConverter {

    private val json = Json

    @TypeConverter
    fun fromIcon(icons: List<Icon>): String {
        return json.encodeToString(icons)
    }

    @TypeConverter
    fun toIcon(data: String): List<Icon> {
        return json.decodeFromString(data)
    }
}
