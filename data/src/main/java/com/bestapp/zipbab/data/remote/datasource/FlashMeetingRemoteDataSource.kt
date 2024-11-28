package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingRequest
import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface FlashMeetingRemoteDataSource {

    suspend fun getMeeting(meetingDocumentID: String): Result<FlashMeetingResponse, NetworkError>

    suspend fun getMeetings(): Result<List<FlashMeetingResponse>, NetworkError>

    suspend fun getMeetingAsHost(userId: String): Result<List<FlashMeetingResponse>, NetworkError>

    suspend fun getMeetingAsMember(userId: String): Result<List<FlashMeetingResponse>, NetworkError>

    suspend fun search(
        keyword: String,
        category: String?,
        onlyVerified: Boolean,
        maxCost: Int,
    ): Result<List<FlashMeetingResponse>, NetworkError>

    suspend fun createMeeting(flashMeetingRequest: FlashMeetingRequest): Result<Boolean, NetworkError>

    suspend fun changeMeetingStatus(meetingDocumentId: String, status: String): Result<Boolean, NetworkError>

    suspend fun addMember(meetingDocumentId: String, userId: String): Result<Boolean, NetworkError>

    suspend fun removeMember(meetingDocumentId: String, userId: String): Result<Boolean, NetworkError>

    suspend fun deleteMeeting(meetingDocumentId: String): Result<Boolean, NetworkError>
}
