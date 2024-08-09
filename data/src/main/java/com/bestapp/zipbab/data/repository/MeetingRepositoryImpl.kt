package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.datasource.remote.MeetingRemoteDataSource
import com.bestapp.zipbab.data.model.remote.MeetingResponse
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MeetingRepositoryImpl @Inject constructor(
    private val meetingRemoteDataSource: MeetingRemoteDataSource,
) : MeetingRepository {

    override suspend fun getMeeting(meetingDocumentID: String): MeetingResponse {
        return meetingRemoteDataSource.getMeeting(meetingDocumentID)
    }

    override suspend fun getMeetings(): List<MeetingResponse> {
        return meetingRemoteDataSource.getMeetings()
    }

    override suspend fun getMeetingByUserDocumentID(userDocumentID: String): List<MeetingResponse> {
        return meetingRemoteDataSource.getMeetingByUserDocumentID(userDocumentID)
    }

    /**
     * @param keyword 검색어(띄워쓰기 인식 가능)
     */
    override suspend fun getSearch(keyword: String): List<MeetingResponse> {
        return meetingRemoteDataSource.getSearch(keyword)
    }

    override suspend fun getFoodMeeting(
        mainMenu: String,
        onlyActivation: Boolean
    ): List<MeetingResponse> {
        return meetingRemoteDataSource.getFoodMeeting(mainMenu, onlyActivation)
    }

    override suspend fun getCostMeeting(
        costType: Int,
        onlyActivation: Boolean
    ): List<MeetingResponse> {
        return meetingRemoteDataSource.getCostMeeting(costType, onlyActivation)
    }

    override suspend fun createMeeting(meetingResponse: MeetingResponse): Boolean {
        return meetingRemoteDataSource.createMeeting(meetingResponse)
    }

    override suspend fun updateAttendanceCheckMeeting(
        meetingDocumentID: String,
        userDocumentID: String,
    ): Boolean {
        return meetingRemoteDataSource.updateAttendanceCheckMeeting(
            meetingDocumentID,
            userDocumentID
        )
    }

    override suspend fun endMeeting(meetingDocumentID: String): Boolean {
        return meetingRemoteDataSource.endMeeting(meetingDocumentID)
    }

    /** 참여 대기중인 멤버리스트에 신청자 추가하기 */
    override suspend fun addPendingMember(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean {
        return meetingRemoteDataSource.addPendingMember(meetingDocumentID, userDocumentID)
    }

    /** 참여 대기중인 멤버를 참여자 리스트로 옮겨주기 */
    override suspend fun approveMember(meetingDocumentID: String, userDocumentID: String): Boolean {
        return meetingRemoteDataSource.approveMember(meetingDocumentID, userDocumentID)
    }

    /** pendingmembers 리스트에서 해당 멤버를 제거하기 */
    override suspend fun rejectMember(meetingDocumentID: String, userDocumentID: String): Boolean {
        return meetingRemoteDataSource.rejectMember(meetingDocumentID, userDocumentID)
    }

    override suspend fun deleteMeeting(meetingDocumentID: String): Boolean {
        return deleteMeeting(meetingDocumentID)
    }

    override suspend fun deleteMeetingMember(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean {
        return deleteMeetingMember(meetingDocumentID, userDocumentID)
    }

    override suspend fun getPendingMeetingByUserDocumentID(userDocumentID: String): List<MeetingResponse> {
        return meetingRemoteDataSource.getPendingMeetingByUserDocumentID(userDocumentID)
    }

}
