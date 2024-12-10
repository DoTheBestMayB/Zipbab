package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserPrivateRelations(
    @Embedded val user: UserPrivateEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
    )
    val notifications: List<NotificationEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
    )
    val joinedFlashMeetings: List<JoinedFlashMeetingEntity>,
)
