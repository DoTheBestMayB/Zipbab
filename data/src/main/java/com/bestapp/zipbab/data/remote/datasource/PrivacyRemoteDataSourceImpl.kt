package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.Privacy
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class PrivacyRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
): PrivacyRemoteDataSource {
    override suspend fun getPrivacyInfo(): Privacy {
        val querySnapshot = firestoreDB.getPrivacyDB().document("use")
            .get()
            .await()

        return querySnapshot.toObject<Privacy>() ?: Privacy()
    }

    override suspend fun getLocationPolicyInfo(): Privacy {
        val querySnapshot = firestoreDB.getPrivacyDB().document("location")
            .get()
            .await()

        return querySnapshot.toObject<Privacy>() ?: Privacy()
    }

    override suspend fun getDeleteRequestInfo(): Privacy {
        val querySnapshot = firestoreDB.getPrivacyDB().document("deleteRequest")
            .get()
            .await()

        return querySnapshot.toObject<Privacy>() ?: Privacy()
    }
}