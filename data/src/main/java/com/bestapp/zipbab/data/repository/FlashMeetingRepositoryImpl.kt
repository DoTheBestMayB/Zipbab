package com.bestapp.zipbab.data.repository

import android.net.Uri
import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingRequest
import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.data.model.remote.user.UserResponse
import com.bestapp.zipbab.data.remote.datasource.FlashMeetingRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.ProfilePostRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.StorageRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.domain.model.flash_meet.FlashMeetStatus
import com.bestapp.zipbab.domain.model.flash_meet.FlashMeeting
import com.bestapp.zipbab.domain.repository.FlashMeetingRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import com.bestapp.zipbab.domain.util.onError
import javax.inject.Inject

internal class FlashMeetingRepositoryImpl @Inject constructor(
    private val flashMeetingRemoteDataSource: FlashMeetingRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val profilePostRemoteDataSource: ProfilePostRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
) : FlashMeetingRepository {

    private suspend fun fetchUserById(id: String): UserResponse {
        return userRemoteDataSource.getUser(id)
    }

    private suspend fun fetchProfilePostById(id: String): ProfilePostResponse {
        return when (val response = profilePostRemoteDataSource.getPost(id)) {
            is Result.Error -> ProfilePostResponse() // TODO : 빈 값을 반환 하는 대신 더 나은 방법이 있을까?
            is Result.Success -> response.data
        }
    }

    override suspend fun getMeeting(meetingDocumentId: String): Result<FlashMeeting, NetworkError> {
        return flashMeetingRemoteDataSource.getMeeting(meetingDocumentId).map { response ->
            response.toDomain(
                fetchUserById = ::fetchUserById,
                fetchProfilePostById = ::fetchProfilePostById,
            )
        }
    }

    override suspend fun getMeetings(): Result<List<FlashMeeting>, NetworkError> {
        return flashMeetingRemoteDataSource.getMeetings().map { response ->
            response.map {
                it.toDomain(
                    fetchUserById = ::fetchUserById,
                    fetchProfilePostById = ::fetchProfilePostById,
                )
            }
        }
    }

    override suspend fun getMeetingAsHost(userId: String): Result<List<FlashMeeting>, NetworkError> {
        return flashMeetingRemoteDataSource.getMeetingAsHost(userId).map { response ->
            response.map {
                it.toDomain(
                    fetchUserById = ::fetchUserById,
                    fetchProfilePostById = ::fetchProfilePostById,
                )
            }
        }
    }

    override suspend fun getMeetingAsMember(userId: String): Result<List<FlashMeeting>, NetworkError> {
        return flashMeetingRemoteDataSource.getMeetingAsMember(userId).map { response ->
            response.map {
                it.toDomain(
                    fetchUserById = ::fetchUserById,
                    fetchProfilePostById = ::fetchProfilePostById,
                )
            }
        }
    }

    override suspend fun search(
        keyword: String,
        category: String?,
        onlyVerified: Boolean,
        maxCost: Int,
    ): Result<List<FlashMeeting>, NetworkError> {
        return flashMeetingRemoteDataSource.search(keyword, category, onlyVerified, maxCost)
            .map { response ->
                response.map {
                    it.toDomain(
                        fetchUserById = ::fetchUserById,
                        fetchProfilePostById = ::fetchProfilePostById,
                    )
                }
            }
    }

    override suspend fun createMeeting(flashMeetingRequest: com.bestapp.zipbab.domain.model.flash_meet.FlashMeetingRequest): Result<Boolean, NetworkError> {
        val imageUrl = if (flashMeetingRequest.profileUri.isNotBlank()) {
            val result =
                storageRemoteDataSource.uploadImage(
                    "flashMeet/profile",
                    Uri.parse(flashMeetingRequest.profileUri)
                )
            when (result) {
                is Result.Error -> return result
                is Result.Success -> result.data
            }
        } else {
            flashMeetingRequest.profileUri
        }
        val request = with(flashMeetingRequest) {
            FlashMeetingRequest(
                hostId = hostId,
                category = category,
                meetingName = meetingName,
                participantCount = participantCount,
                cost = cost,
                description = description,
                address = address,
                longitude = longitude,
                latitude = latitude,
                zipCode = zipCode,
                date = date,
                hour = hour,
                minute = minute,
                isApprovalRequired = isApprovalRequired,
                verification = verification,
                profileUri = imageUrl,
            )
        }

        return flashMeetingRemoteDataSource.createMeeting(request)
    }

    override suspend fun changeMeetingStatus(
        meetingDocumentId: String,
        status: FlashMeetStatus
    ): Result<Boolean, NetworkError> {
        return flashMeetingRemoteDataSource.changeMeetingStatus(
            meetingDocumentId,
            status.name.lowercase()
        )
    }

    override suspend fun addMember(
        meetingDocumentId: String,
        userId: String
    ): Result<Boolean, NetworkError> {
        return flashMeetingRemoteDataSource.addMember(meetingDocumentId, userId)
    }

    override suspend fun deleteMeeting(meetingDocumentId: String): Result<Boolean, NetworkError> {
        val meeting =
            when (val response = flashMeetingRemoteDataSource.getMeeting(meetingDocumentId)) {
                is Result.Error -> return response
                is Result.Success -> response.data
            }
        val deleteMeetingResponse = flashMeetingRemoteDataSource.deleteMeeting(meetingDocumentId)
        if (deleteMeetingResponse is Result.Error) {
            return deleteMeetingResponse
        }

        storageRemoteDataSource.deleteImage(meeting.mainImageUrl)
            .onError { exception ->
                // TODO : crashlytics 이용해서 기록하고 cloud function으로 삭제하도록
//                Firebase.crashlytics.log("Failed to delete image: flashMeet/profile/${meeting.mainImageUrl}")
//                Firebase.crashlytics.recordException(exception)
            }

        return Result.Success(true)
    }

    override suspend fun removeMember(
        meetingDocumentId: String,
        userId: String
    ): Result<Boolean, NetworkError> {
        return flashMeetingRemoteDataSource.removeMember(meetingDocumentId, userId)
    }

}
