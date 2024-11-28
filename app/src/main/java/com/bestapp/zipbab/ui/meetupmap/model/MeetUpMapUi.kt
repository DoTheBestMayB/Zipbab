package com.bestapp.zipbab.ui.meetupmap.model

data class MeetUpMapUi(
    val meetingDocumentID: String,
    val title: String,
    val titleImage: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val zipCode: String,
    val date: String,
    val hour: Int,
    val minute: Int,
    val participantCount: Int,
    val description: String,
    val mainMenu: String,
    val costValueByPerson: Int,
    val hostUserDocumentID: String,
    val members: List<String>,
    val pendingMembers: List<String>,
    val attendanceCheck: List<String>,
    val activation: Boolean,
    val distance: Double,
    val distanceByUser: String,
    val isHost: Boolean,
) {
    val shortTitle: String
        // TODO: 하드코딩 제거
        get() = if (title.length > 15) {
            String.format("%s...", title.substring(0, 14))
        } else {
            title
        }
}
