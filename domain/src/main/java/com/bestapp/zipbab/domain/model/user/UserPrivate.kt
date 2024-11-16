package com.bestapp.zipbab.domain.model.user


data class UserPrivate(
    val id: String,
    val pw: String,
    val email: String,
    val phone: String,
    val notifications: List<Notification>,
    val joinedMeetings: List<JoinedMeeting>
)
