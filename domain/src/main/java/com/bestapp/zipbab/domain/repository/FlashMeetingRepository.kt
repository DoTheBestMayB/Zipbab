package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.flash_meet.FlashMeetStatus
import com.bestapp.zipbab.domain.model.flash_meet.FlashMeeting
import com.bestapp.zipbab.domain.model.flash_meet.FlashMeetingRequest
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface FlashMeetingRepository {
    suspend fun getMeeting(meetingDocumentId: String): Result<FlashMeeting, NetworkError>

    suspend fun getMeetings(): Result<List<FlashMeeting>, NetworkError>

    suspend fun getMeetingAsHost(userId: String): Result<List<FlashMeeting>, NetworkError>

    suspend fun getMeetingAsMember(userId: String): Result<List<FlashMeeting>, NetworkError>

    suspend fun search(
        keyword: String,
        category: String? = null,
        onlyVerified: Boolean = false,
        maxCost: Int = Int.MAX_VALUE,
    ): Result<List<FlashMeeting>, NetworkError>

    suspend fun createMeeting(flashMeetingRequest: FlashMeetingRequest): Result<Boolean, NetworkError>

    suspend fun changeMeetingStatus(meetingDocumentId: String, status: FlashMeetStatus): Result<Boolean, NetworkError>

    suspend fun addMember(meetingDocumentId: String, userId: String): Result<Boolean, NetworkError>

    suspend fun deleteMeeting(meetingDocumentId: String): Result<Boolean, NetworkError>

    suspend fun removeMember(meetingDocumentId: String, userId: String): Result<Boolean, NetworkError>
}
