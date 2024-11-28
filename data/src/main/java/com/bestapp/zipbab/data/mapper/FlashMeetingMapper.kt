package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.local.room.entity.JoinedFlashMeetingEntity
import com.bestapp.zipbab.data.local.room.entity.MeetTypeEntity
import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingResponse
import com.bestapp.zipbab.data.model.remote.flash_meet.IngredientResponse
import com.bestapp.zipbab.data.model.remote.flash_meet.MenuResponse
import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.data.model.remote.user.UserResponse
import com.bestapp.zipbab.domain.model.flash_meet.FlashMeetStatus
import com.bestapp.zipbab.domain.model.flash_meet.FlashMeeting
import com.bestapp.zipbab.domain.model.flash_meet.Ingredient
import com.bestapp.zipbab.domain.model.flash_meet.Menu
import com.bestapp.zipbab.domain.model.user.JoinedFlashMeeting
import com.bestapp.zipbab.domain.model.user.MeetType

fun JoinedFlashMeetingEntity.toDomain(): JoinedFlashMeeting {
    return JoinedFlashMeeting(
        meetId = meetId,
        joinedAt = joinedAt,
        type = type.toDomain(),
        hasReviewed = hasReviewed,
        reviewId = reviewId,
    )
}

fun JoinedFlashMeeting.toEntity(): JoinedFlashMeetingEntity {
    return JoinedFlashMeetingEntity(
        meetId = meetId,
        joinedAt = joinedAt,
        type = type.toEntity(),
        hasReviewed = hasReviewed,
        reviewId = reviewId,
    )
}

fun MeetTypeEntity.toDomain(): MeetType {
    return when (this) {
        MeetTypeEntity.FLASH_MEET -> MeetType.FLASH_MEET
        MeetTypeEntity.MEET -> MeetType.MEET
    }
}

fun MeetType.toEntity(): MeetTypeEntity {
    return when (this) {
        MeetType.FLASH_MEET -> MeetTypeEntity.FLASH_MEET
        MeetType.MEET -> MeetTypeEntity.MEET
    }
}

suspend fun FlashMeetingResponse.toDomain(
    fetchUserById: suspend (String) -> UserResponse,
    fetchProfilePostById: suspend (String) -> ProfilePostResponse,
): FlashMeeting {
    return FlashMeeting(
        uuid = id,
        title = title,
        status = when (status) {
            FlashMeetStatus.ACTIVE.name.lowercase() -> FlashMeetStatus.ACTIVE
            FlashMeetStatus.COMPLETED.name.lowercase() -> FlashMeetStatus.COMPLETED
            FlashMeetStatus.CANCELED.name.lowercase() -> FlashMeetStatus.CANCELED
            else -> FlashMeetStatus.NONE
        },
        mainImageUrl = mainImageUrl,
        participantCount = participantCount,
        costPerPerson = costPerPerson,
        address = address,
        longitude = longitude,
        latitude = latitude,
        zipCode = zipCode,
        dateTime = dateTime,
        description = description,
        category = category,
        isApprovalRequired = isApprovalRequired,
        verification = verification,
        hostUser = hostUser,
        members = members.map { fetchUserById(id).toDomain(fetchProfilePostById) },
        menu = menu.map { it.toDomain() },
        ingredients = ingredients.map { it.toDomain() },
    )
}

fun MenuResponse.toDomain(): Menu {
    return Menu(
        uuid = documentId,
        name = name,
        description = description,
        imageUrl = imageUrl,
    )
}

fun IngredientResponse.toDomain(): Ingredient {
    return Ingredient(
        uuid = documentId,
        name = name,
        quantity = quantity,
        discount = discount,
        imageUrl = imageUrl,
        remainingQuantity = remainingQuantity
    )
}
