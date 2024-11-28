package com.bestapp.zipbab.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param posts Post Document ID
 */
@Entity
data class UserEntity(
    @PrimaryKey val id: String = "",
    val nickname: String = "",
    val profileImageUrl: String = "",
    val posts: List<String> = emptyList(),
    val temperature: Double = 0.0,
    val meetingCount: Int = 0,
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
)
