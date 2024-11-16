package com.bestapp.zipbab.data.model.remote.user

/**
 * @property id 아이디
 * @property nickname 닉네임
 * @property profileImageUrl 프로필 이미지
 * @property temperatures 매너 온도
 * @property meetingCount 모임 횟수
 * @property posts 포스트 id
 * @property isEmailVerified 이메일 인증 여부
 * @property isPhoneVerified 인증된 전화번호
 */
data class UserResponse(
    val id: String = "",
    val nickname: String = "",
    val profileImageUrl: String = "",
    val posts: List<String> = emptyList(),
    val temperatures: List<TemperatureResponse> = emptyList(),
    val meetingCount: Int = 0,
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
)
