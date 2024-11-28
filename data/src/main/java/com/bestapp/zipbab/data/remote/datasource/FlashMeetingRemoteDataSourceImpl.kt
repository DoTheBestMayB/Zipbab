package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingCreateForm
import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingRequest
import com.bestapp.zipbab.data.model.remote.flash_meet.FlashMeetingResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.bestapp.zipbab.data.util.createZonedDateTime
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FlashMeetingRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : FlashMeetingRemoteDataSource {

    private suspend fun Query.toMeetings(): List<FlashMeetingResponse> {
        val querySnapshot = this.get().await()

        return querySnapshot.documents.mapNotNull { document ->
            document.toObject<FlashMeetingResponse>()?.copy(id = document.id)
        }
    }

    private fun createSearchKeywords(vararg fields: String): List<String> {
        val keywords = mutableSetOf<String>()

        for (field in fields) {
            keywords.addAll(field.split(" "))
        }
        return keywords.toList()
    }


    override suspend fun getMeeting(meetingDocumentID: String): Result<FlashMeetingResponse, NetworkError> {
        return safeFirebaseCall {
            val documentSnapshot = firestoreDB.getFlashMeetingDB().document(meetingDocumentID)
                .get()
                .await()

            documentSnapshot.toObject<FlashMeetingResponse>() ?: FlashMeetingResponse()
        }

    }

    override suspend fun getMeetings(): Result<List<FlashMeetingResponse>, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB()
                .toMeetings()
        }
    }

    override suspend fun getMeetingAsHost(userId: String): Result<List<FlashMeetingResponse>, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB().whereEqualTo("hostUser", userId)
                .toMeetings()
        }
    }

    override suspend fun getMeetingAsMember(userId: String): Result<List<FlashMeetingResponse>, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB().whereArrayContains("members", userId)
                .toMeetings()
        }
    }

    override suspend fun search(
        keyword: String,
        category: String?,
        onlyVerified: Boolean,
        maxCost: Int,
    ): Result<List<FlashMeetingResponse>, NetworkError> {
        return safeFirebaseCall {
            val keyWords = keyword.split(" ")

            var query = firestoreDB.getFlashMeetingDB()
                .whereEqualTo("status", "active")
                .whereEqualTo("onlyVerified", onlyVerified)
                .whereLessThanOrEqualTo("costPerPerson", maxCost)
            if (category != null) {
                query = query.whereEqualTo("category", category)
            }
            query = query.whereArrayContainsAny("searchKeywords", keyWords)

            query.toMeetings()
        }
    }

    override suspend fun createMeeting(flashMeetingRequest: FlashMeetingRequest): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            val requestForm = with(flashMeetingRequest) {
                FlashMeetingCreateForm(
                    title = meetingName,
                    searchKeywords = createSearchKeywords(meetingName, description),
                    mainImageUrl = profileUri,
                    address = address,
                    longitude = longitude,
                    latitude = latitude,
                    zipCode = zipCode,
                    dateTime = createZonedDateTime(date, hour, minute),
                    participantCount = participantCount,
                    description = description,
                    category = category,
                    isApprovalRequired = isApprovalRequired,
                    verification = verification,
                    costPerPerson = cost,
                    hostUser = hostId,
                    members = listOf(),
                    menu = listOf(),
                    ingredients = listOf(),
                )
            }
            firestoreDB.getFlashMeetingDB()
                .add(requestForm)
                .doneSuccessful()
        }
    }

    override suspend fun changeMeetingStatus(
        meetingDocumentId: String,
        status: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB().document(meetingDocumentId)
                .update("status", status)
                .doneSuccessful()
        }
    }

    override suspend fun addMember(
        meetingDocumentId: String,
        userId: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB().document(meetingDocumentId)
                .update("members", FieldValue.arrayUnion(userId))
                .doneSuccessful()
        }
    }

    override suspend fun removeMember(
        meetingDocumentId: String,
        userId: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB().document(meetingDocumentId)
                .update("members", FieldValue.arrayRemove(userId))
                .doneSuccessful()
        }
    }

    override suspend fun deleteMeeting(meetingDocumentId: String): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getFlashMeetingDB().document(meetingDocumentId)
                .delete()
                .doneSuccessful()
        }
    }
}
