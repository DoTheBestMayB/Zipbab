package com.bestapp.zipbab.model

/**
 * @property verifiedEmail 인증된 이메일
 * @property verifiedPhone 인증된 전화번호
 */
data class UserUiState(
    val userDocumentID: String = "",
    val nickname: String = "",
    val id: String = "",
    val pw: String = "",
    val profileImage: String = "",
    val temperature: Double = 0.0,
    val meetingCount: Int = 0,
    val notificationUiState: List<NotificationUiState> = listOf(),
    val meetingReviews: List<String> = listOf(),
    val postDocumentIds: List<String> = listOf(),
    val placeLocationUiState: PlaceLocationUiState = PlaceLocationUiState(),
    val verifiedEmail: String = "",
    val verifiedPhone: String = "",
) {
    val isLoggedIn: Boolean
        get() = userDocumentID.isNotBlank()
}
