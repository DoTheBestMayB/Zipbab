package com.bestapp.zipbab.ui.mettinglist

import com.bestapp.zipbab.data.model.remote.MeetingResponse

data class MeetingListUi(
    val meetingDocumentID: String,
    val title: String,
    val titleImage: String,
    val address: String,
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
    // TODO: User data class 수정된 뒤, 값 셋업
    val isDoneReview: Boolean = false,
    val isHost: Boolean = false,
)

fun MeetingResponse.toMeetingListUi(
    isDoneReview: Boolean,
    isHost: Boolean,
) = MeetingListUi(
    meetingDocumentID = meetingDocumentID,
    title = title,
    titleImage = titleImage,
    address = address,
    zipCode = zipCode,
    date = date,
    hour = hour,
    minute = minute,
    participantCount = participantCount,
    description = description,
    mainMenu = category,
    costValueByPerson = costValueByPerson,
    hostUserDocumentID = hostUserDocumentID,
    members = members,
    pendingMembers = pendingMembers,
    attendanceCheck = attendanceCheck,
    activation = activation,
    isDoneReview = isDoneReview,
    isHost = isHost
)
