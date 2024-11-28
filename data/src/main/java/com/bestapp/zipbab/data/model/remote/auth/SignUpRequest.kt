package com.bestapp.zipbab.data.model.remote.auth

import com.bestapp.zipbab.data.model.remote.user.JoinedMeetingResponse

data class SignUpRequest(
    val id: String = "",
    val pw: String = "",
    val nickname: String = "",
    val email: String = "",
    val phone: String = "",
    val notifications: List<String> = emptyList(),
    val joinedMeetings: List<JoinedMeetingResponse> = emptyList(),
)
