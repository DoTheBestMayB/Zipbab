package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.term.TermsAndConditionResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class PrivacyRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : PrivacyRemoteDataSource {
    override suspend fun getPrivacyPolicy(): Result<TermsAndConditionResponse, NetworkError> {
        return safeFirebaseCall<TermsAndConditionResponse> {
            val documentSnapshot = firestoreDB.getTermsDB().document("privacy")
                .get()
                .await()
            documentSnapshot.toObject<TermsAndConditionResponse>()
        }

    }

    override suspend fun getLocationPolicy(): Result<TermsAndConditionResponse, NetworkError> {
        return safeFirebaseCall<TermsAndConditionResponse> {
            val documentSnapshot = firestoreDB.getTermsDB().document("location")
                .get()
                .await()
            documentSnapshot.toObject<TermsAndConditionResponse>()
        }
    }

    override suspend fun getDeleteRequestUrl(): Result<String, NetworkError> {
        return safeFirebaseCall<String> {
            val documentSnapshot = firestoreDB.getTermsDB().document("deleteRequest")
                .get()
                .await()
            documentSnapshot.getString("url")
        }
    }
}
