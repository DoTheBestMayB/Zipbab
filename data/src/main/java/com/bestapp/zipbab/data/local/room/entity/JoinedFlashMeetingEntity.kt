package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bestapp.zipbab.data.local.room.serializer.ZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserPrivateEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["userId"])],
)
@Serializable
data class JoinedFlashMeetingEntity(
    @PrimaryKey val meetId: String,
    val userId: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val joinedAt: ZonedDateTime,
    val type: String,
    val hasReviewed: Boolean,
    val reviewId: String,
)
