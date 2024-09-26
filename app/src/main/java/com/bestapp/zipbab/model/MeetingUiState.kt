package com.bestapp.zipbab.model

data class MeetingUiState(
    val meetingDocumentID: String = "",
    val title: String = "",
    val titleImage: String = "",
    val address: String = "",
    val zipCode: String = "",
    val date: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val participantCount: Int = 0,
    val description: String = "",
    val mainMenu: String = "",
    val costValueByPerson: Int = 0,
    val hostUserDocumentID: String = "",
    val members: List<String> = emptyList(),
    val pendingMembers: List<String> = emptyList(),
    val attendanceCheck: List<String> = emptyList(),
    val activation: Boolean = false,
)
