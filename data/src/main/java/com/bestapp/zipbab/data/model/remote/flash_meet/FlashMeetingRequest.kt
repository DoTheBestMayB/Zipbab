package com.bestapp.zipbab.data.model.remote.flash_meet

data class FlashMeetingRequest(
    val hostDocumentID: String,
    val category: String,
    val meetingName: String,
    val participantCount: Int,
    val cost: Int,
    val description: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val zipCode: String,
    val date: String,
    val hour: Int,
    val minute: Int,
    val isApprovalRequired: Boolean,
    val verification: String,
    val profileUri: String = "",
)
