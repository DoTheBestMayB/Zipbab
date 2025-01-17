package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.local.room.entity.UserPrivateEntity
import com.bestapp.zipbab.domain.model.user.UserPrivate

fun UserPrivateEntity.toDomain(): UserPrivate {
    return UserPrivate(
        id = id,
        pw = pw,
        email = email,
        phone = phone,
        notifications = notifications.map {
            it.toDomain()
        },
        joinedFlashMeetings = joinedFlashMeetings.map {
            it.toDomain()
        },
    )
}

fun UserPrivate.toEntity(): UserPrivateEntity {
    return UserPrivateEntity(
        id = id,
        pw = pw,
        email = email,
        phone = phone,
        notifications = notifications.map {
            it.toEntity()
        },
        joinedFlashMeetings = joinedFlashMeetings.map {
            it.toEntity()
        },
    )
}
