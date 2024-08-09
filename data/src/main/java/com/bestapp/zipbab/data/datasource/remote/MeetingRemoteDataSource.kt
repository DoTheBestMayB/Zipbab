package com.bestapp.zipbab.data.datasource.remote

import com.bestapp.zipbab.data.model.remote.MeetingResponse

interface MeetingRemoteDataSource {

    suspend fun getMeeting(meetingDocumentID: String): MeetingResponse

    suspend fun getMeetings(): List<MeetingResponse>

    suspend fun getMeetingByUserDocumentID(userDocumentID: String): List<MeetingResponse>

    suspend fun getSearch(keyword: String): List<MeetingResponse>

    suspend fun getFoodMeeting(
        mainMenu: String,
        onlyActivation: Boolean
    ): List<MeetingResponse>

    suspend fun getCostMeeting(
        costType: Int,
        onlyActivation: Boolean
    ): List<MeetingResponse>

    suspend fun createMeeting(meetingResponse: MeetingResponse): Boolean

    suspend fun getHostTemperature(hostDocumentID: String): Double

    suspend fun updateAttendanceCheckMeeting(
        meetingDocumentID: String,
        userDocumentID: String,
    ): Boolean

    suspend fun endMeeting(meetingDocumentID: String): Boolean

    suspend fun addPendingMember(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean

    suspend fun approveMember(meetingDocumentID: String, userDocumentID: String): Boolean

    suspend fun rejectMember(meetingDocumentID: String, userDocumentID: String): Boolean

    suspend fun deleteMeeting(meetingDocumentID: String): Boolean

    suspend fun deleteMeetingMember(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean

    suspend fun getPendingMeetingByUserDocumentID(userDocumentID: String): List<MeetingResponse>
}