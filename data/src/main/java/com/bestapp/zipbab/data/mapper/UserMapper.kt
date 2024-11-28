package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.data.model.remote.user.TemperatureResponse
import com.bestapp.zipbab.data.model.remote.user.UserResponse
import com.bestapp.zipbab.domain.model.user.ProfilePost
import com.bestapp.zipbab.domain.model.user.Temperature
import com.bestapp.zipbab.domain.model.user.User

suspend fun UserResponse.toDomain(
    fetchProfilePostById: suspend (String) -> ProfilePostResponse,
): User {
    return User(
        id = id,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        posts = posts.map { id ->
            fetchProfilePostById(id).toDomain()
        },
        temperatures = temperatures.map { it.toDomain() },
        meetingCount = meetingCount,
        isEmailVerified = isEmailVerified,
        isPhoneVerified = isPhoneVerified,
    )
}

fun ProfilePostResponse.toDomain(): ProfilePost {
    return ProfilePost(
        uuid = documentId,
        writer = writer,
        images = images,
        createdAt = createdAt,
    )
}

fun TemperatureResponse.toDomain(): Temperature {
    return Temperature(
        num = num,
        recordedAt = recordedAt,
    )
}
