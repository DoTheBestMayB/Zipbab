package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.notice.AnnouncementNotificationResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoticeRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
): NoticeRemoteDataSource {

    override suspend fun fetchAnnouncement(): Result<AnnouncementNotificationResponse?, NetworkError> {
        return safeFirebaseCall<AnnouncementNotificationResponse?> {
            val documentSnapshot = firestoreDB.getNoticeDB().document("announcement")
                .get()
                .await()

            if (documentSnapshot.exists()) {
                documentSnapshot.toObject<AnnouncementNotificationResponse>()
            } else {
                null
            }
        }
    }
}
