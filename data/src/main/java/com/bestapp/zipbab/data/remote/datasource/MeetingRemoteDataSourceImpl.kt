package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.MeetingResponse
import com.bestapp.zipbab.data.model.remote.UserResponse
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MeetingRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : MeetingRemoteDataSource {

    private suspend fun Query.toMeetings(): List<MeetingResponse> {
        val querySnapshot = this.get().await()

        return querySnapshot.documents.mapNotNull { document ->
            document.toObject<MeetingResponse>()
        }
    }

    override suspend fun getMeeting(meetingDocumentID: String): MeetingResponse {
        val meeting = firestoreDB.getMeetingDB()
            .whereEqualTo("meetingDocumentID", meetingDocumentID)
            .get()
            .await()

        for (document in meeting) {
            return document.toObject<MeetingResponse>()
        }

        return MeetingResponse()
    }

    override suspend fun getMeetings(): List<MeetingResponse> {
        return firestoreDB.getMeetingDB()
            .toMeetings()
    }

    override suspend fun getMeetingByUserDocumentID(userDocumentID: String): List<MeetingResponse> {
        return firestoreDB.getMeetingDB().where(
            Filter.or(
                Filter.arrayContains("members", userDocumentID),
                Filter.equalTo("hostUserDocumentID", userDocumentID)
            )
        ).toMeetings()
    }

    override suspend fun getSearch(keyword: String): List<MeetingResponse> {
        val activateMeetings = firestoreDB.getMeetingDB()
            .whereEqualTo("activation", true)
            .toMeetings()

        val queries = keyword.split(" ")

        return activateMeetings.filter { meeting ->
            queries.any { text ->
                text in meeting.title
            }
        }
    }

    override suspend fun getFoodMeeting(
        mainMenu: String,
        onlyActivation: Boolean
    ): List<MeetingResponse> {
        var query = firestoreDB.getMeetingDB()
            .whereEqualTo("mainMenu", mainMenu)

        if (onlyActivation) {
            query = query.whereEqualTo("activation", true)
        }

        return query.toMeetings()
    }

    override suspend fun getCostMeeting(
        costType: Int,
        onlyActivation: Boolean
    ): List<MeetingResponse> {
        var query = firestoreDB.getMeetingDB()
            .whereEqualTo("costTypeByPerson", costType)

        if (onlyActivation) {
            query = query.whereEqualTo("activation", true)
        }

        return query.toMeetings()
    }

    override suspend fun createMeeting(meetingResponse: MeetingResponse): Boolean {
        val documentRef = firestoreDB.getMeetingDB()
            .add(meetingResponse)
            .await()

        val meetingDocumentID = documentRef.id

        return firestoreDB.getMeetingDB().document(meetingDocumentID)
            .update("meetingDocumentID", meetingDocumentID)
            .doneSuccessful()
    }

    override suspend fun getHostTemperature(hostDocumentID: String): Double {
        val querySnapshot = firestoreDB.getUsersDB()
            .whereEqualTo("userDocumentID", hostDocumentID)
            .get()
            .await()

        val hostUser = querySnapshot.documents.first()
        return hostUser.toObject<UserResponse>()?.temperature ?: Double.MIN_VALUE
    }

    override suspend fun updateAttendanceCheckMeeting(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean {
        return firestoreDB.getMeetingDB().document(meetingDocumentID)
            .update("attendanceCheck", FieldValue.arrayUnion(userDocumentID))
            .doneSuccessful()
    }

    override suspend fun endMeeting(meetingDocumentID: String): Boolean {
        return firestoreDB.getMeetingDB().document(meetingDocumentID)
            .update("activation", false)
            .doneSuccessful()
    }

    override suspend fun addPendingMember(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean {
        return firestoreDB.getMeetingDB().document(meetingDocumentID)
            .update("pendingMembers", FieldValue.arrayUnion(userDocumentID))
            .doneSuccessful()
    }

    override suspend fun approveMember(meetingDocumentID: String, userDocumentID: String): Boolean {
        val meetingRef = firestoreDB.getMeetingDB().document(meetingDocumentID)

        return firestoreDB.runTransaction { transaction ->
            transaction.update(meetingRef, "pendingMembers", FieldValue.arrayRemove(userDocumentID))
            transaction.update(meetingRef, "members", FieldValue.arrayUnion(userDocumentID))
        }.doneSuccessful()
    }

    override suspend fun rejectMember(meetingDocumentID: String, userDocumentID: String): Boolean {
        return firestoreDB.getMeetingDB().document(meetingDocumentID)
            .update("pendingMembers", FieldValue.arrayRemove(userDocumentID))
            .doneSuccessful()
    }

    override suspend fun deleteMeeting(meetingDocumentID: String): Boolean {
        return firestoreDB.getMeetingDB().document(meetingDocumentID)
            .delete()
            .doneSuccessful()
    }

    override suspend fun deleteMeetingMember(
        meetingDocumentID: String,
        userDocumentID: String
    ): Boolean {
        val meetingRef = firestoreDB.getMeetingDB().document(meetingDocumentID)

        return firestoreDB.runTransaction { transition ->
            transition.update(meetingRef, "pendingMembers", FieldValue.arrayRemove(userDocumentID))
            transition.update(meetingRef, "members", FieldValue.arrayUnion(userDocumentID))
        }.doneSuccessful()
    }

    override suspend fun getPendingMeetingByUserDocumentID(userDocumentID: String): List<MeetingResponse> {
        return firestoreDB.getMeetingDB()
            .where(Filter.arrayContains("pendingMembers", userDocumentID))
            .toMeetings()
    }
}