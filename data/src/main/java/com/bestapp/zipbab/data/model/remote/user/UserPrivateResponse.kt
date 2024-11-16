package com.bestapp.zipbab.data.model.remote.user

internal data class UserPrivateResponse(
    val id: String = "",
    val pw: String = "",
    val email: String = "",
    val phone: String = "",
    val notifications: List<String> = emptyList(),
    val joinedMeetings: List<JoinedMeetingResponse> = emptyList(),
)
