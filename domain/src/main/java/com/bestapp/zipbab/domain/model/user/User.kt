package com.bestapp.zipbab.domain.model.user

data class User(
    val id: String,
    val nickname: String,
    val profileImageUrl: String,
    val posts: List<ProfilePost>,
    val temperatures: List<Temperature>,
    val meetingCount: Int,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
)
